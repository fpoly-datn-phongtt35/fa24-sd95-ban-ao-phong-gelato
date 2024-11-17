package com.example.datn.repositories;

import com.example.datn.dto.Bill.BillDetailDtoInterface;
import com.example.datn.dto.Bill.BillDetailProduct;
import com.example.datn.dto.Bill.BillDtoInterface;
import com.example.datn.dto.Refund.RefundDto;
import com.example.datn.dto.Statistic.OrderStatistic;
import com.example.datn.entities.Bill;
import com.example.datn.entities.enumClass.BillStatus;
import com.example.datn.entities.enumClass.InvoiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository  extends JpaRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {
    @Query(value = "SELECT DISTINCT b.id AS maHoaDon, " +
            "b.code AS maDinhDanh, " +
            "a.name AS hoVaTen, " +
            "a.phone_Number AS soDienThoai, " +
            "b.create_Date AS ngayTao, " +
            "b.amount AS tongTien, " +
            "b.status AS trangThai, " +
            "b.invoice_Type AS loaiDon, " +
            "pm.name AS hinhThucThanhToan, " +
            "COALESCE(br.code, '') AS maDoiTra, " +
            "pmt.order_Id AS maGiaoDich " +
            "FROM Bill b " +
            "JOIN Payment pmt ON b.id = pmt.bill_id " +
            "LEFT JOIN Customer a ON b.customer_id = a.id " +
            "LEFT JOIN bill_detail bd ON b.id = bd.bill_id " +
            "LEFT JOIN Payment_Method pm ON b.payment_Method_id = pm.id " +
            "LEFT JOIN Bill_Return br ON b.id = br.bill_id",
            nativeQuery = true)
    Page<BillDtoInterface> listBill(Pageable pageable);

    @Query(value = "SELECT DISTINCT \n" +
            "    b.id AS maHoaDon,\n" +
            "    b.code AS maDinhDanh, \n" +
            "    a.name AS hoVaTen, \n" +
            "    a.phone_Number AS soDienThoai, \n" +
            "    b.create_Date AS ngayTao, \n" +
            "    b.amount AS tongTien, \n" +
            "    b.status AS trangThai, \n" +
            "    b.invoice_Type AS loaiDon, \n" +
            "    pm.name AS hinhThucThanhToan, \n" +
            "    COALESCE(br.code, '') AS maDoiTra\n" +
            "FROM \n" +
            "    Bill b\n" +
            "LEFT JOIN \n" +
            "    Customer a ON b.customer_id = a.id\n" +
            "LEFT JOIN \n" +
            "    Bill_Detail bd ON b.id = bd.bill_id\n" +
            "LEFT JOIN \n" +
            "    Payment_Method pm ON b.payment_Method_id = pm.id\n" +
            "LEFT JOIN \n" +
            "    Bill_Return br ON b.id = br.bill_id\n" +
            "WHERE \n" +
            "    (:maDinhDanh IS NULL OR b.code LIKE CONCAT('%', :maDinhDanh, '%'))\n" +
            "    AND (:ngayTaoStart IS NULL OR :ngayTaoEnd IS NULL OR b.create_Date BETWEEN :ngayTaoStart AND :ngayTaoEnd)\n" +
            "    AND (:trangThai IS NULL OR b.status = :trangThai)\n" +
            "    AND (:loaiDon IS NULL OR b.invoice_Type = :loaiDon)\n" +
            "    AND (:soDienThoai IS NULL OR a.phone_Number IS NULL OR a.phone_Number LIKE CONCAT('%', :soDienThoai, '%'))\n" +
            "    AND (:hoVaTen IS NULL OR a.name IS NULL OR a.name LIKE CONCAT('%', :hoVaTen, '%'))\n",
    nativeQuery = true)
    Page<BillDtoInterface> listSearchBill(
            @Param("maDinhDanh") String maDinhDanh,
            @Param("ngayTaoStart") LocalDateTime ngayTaoStart,
            @Param("ngayTaoEnd") LocalDateTime ngayTaoEnd,
            @Param("trangThai") BillStatus trangThai,
            @Param("loaiDon") InvoiceType loaiDon,
            @Param("soDienThoai") String soDienThoai,
            @Param("hoVaTen") String hoVaTen,
            Pageable pageable);

    @Modifying
    @Query(value = "UPDATE bill SET status=:status WHERE id=:id", nativeQuery = true)
    @Transactional
    int updateStatus(@Param("status") String status,@Param("id") Long id);


    @Query(value = "select distinct b.id as maDonHang,b.code as maDinhDanh,b.billing_address as diaChi," +
            " b.amount as tongTien,b.promotion_price as tienKhuyenMai,a.name as tenKhachHang," +
            "a.phone_number as soDienThoai,a.email as email, b.status as trangThaiDonHang, pmt.order_id as maGiaoDich, " +
            "pm.name as phuongThucThanhToan,b.invoice_type as loaiHoaDon, dc.code as voucherName, b.create_date as createdDate " +
            "from bill b full join customer a on b.customer_id=a.id full join discount_code dc on b.discount_code_id=dc.id" +
            " full join bill_detail bd on b.id=bd.bill_id join payment pmt on b.id = pmt.bill_id left join payment_method pm on b.payment_method_id=pm.id where b.id=:maHoaDon",nativeQuery = true)
    BillDetailDtoInterface getBillDetail(@Param("maHoaDon") Long maHoaDon);

    @Query(value = "select pd.id, bd.id as billDetailId, p.id as productId, p.name as tenSanPham,c.name as tenMau, s.name as kichCo, bd.moment_price as giaTien,bd.quantity as soLuong, bd.moment_price*bd.quantity as tongTien,  (\n" +
            "           SELECT top(1) link\n" +
            "           FROM image\n" +
            "           WHERE p.id = image.product_id\n" +

            "       ) AS imageUrl " +
            "from bill b join bill_detail bd on b.id=bd.bill_id join" +
            " product_detail pd on bd.product_detail_id =pd.id join" +
            " product p on pd.product_id=p.id join color c on pd.color_id=c.id join size s on pd.size_id = s.id " +
            "where b.id=:maHoaDon",nativeQuery = true)
    List<BillDetailProduct> getBillDetailProduct(@Param("maHoaDon") Long maHoaDon);

    Bill findTopByOrderByIdDesc();

    Page<Bill> findAllByStatusAndCustomer_Account_Id(BillStatus status, Long id, Pageable pageable);

    Page<Bill> findByCustomer_Account_Id(Long id, Pageable pageable);

    @Query(value = "select * from bill b where DATEDIFF(DAY, b.create_date, GETDATE()) <= 7 and b.status='HOAN_THANH'", nativeQuery = true)
    Page<Bill> findValidBillToReturn(Pageable pageable);

    @Query(value = "SELECT \n" +
            "    COALESCE(SUM(b.amount), 0) - COALESCE(SUM(br.return_money), 0) + COALESCE(SUM(rd.quantity_return * pd.price), 0) AS total\n" +
            "FROM \n" +
            "    bill b \n" +
            "    LEFT JOIN bill_return br ON b.id = br.bill_id \n" +
            "    LEFT JOIN return_detail rd ON br.id = rd.id \n" +
            "    LEFT JOIN product_detail pd ON rd.product_detail_id = pd.id \n" +
            "WHERE \n" +
            "    b.status = 'HOAN_THANH';", nativeQuery = true)
    Double calculateTotalRevenue();

    @Query(value = "SELECT " +
            "COALESCE(SUM(b.amount), 0) - COALESCE(SUM(br.return_money), 0) + COALESCE(SUM(rd.quantity_return * pd.price), 0) AS total " +
            "FROM bill b " +
            "LEFT JOIN bill_return br ON b.id = br.bill_id " +
            "LEFT JOIN return_detail rd ON br.id = rd.id " +
            "LEFT JOIN product_detail pd ON rd.product_detail_id = pd.id " +
            "WHERE b.status = 'HOAN_THANH' " +
            "AND (b.create_date BETWEEN CONVERT(DATETIME, :startDate, 120) AND CONVERT(DATETIME, :endDate, 120))", nativeQuery = true)
    Double calculateTotalRevenueFromDate(String startDate, String endDate);


    @Query(value = "SELECT CONVERT(DATE, create_date) AS date, COALESCE(SUM(b.amount), 0) - COALESCE(SUM(br.return_money), 0) + COALESCE(SUM(rd.quantity_return * pd.price), 0) AS revenue\n" +
            "FROM bill b LEFT JOIN bill_return br ON b.id = br.bill_id LEFT JOIN return_detail rd ON br.id = rd.id\n" +
            "LEFT JOIN product_detail pd ON rd.product_detail_id = pd.id " +
            "WHERE (YEAR(b.create_date) = :year AND MONTH(b.create_date) = :month AND b.status='HOAN_THANH' ) " +
            "GROUP BY CONVERT(DATE, create_date)\n" +
            "ORDER BY CONVERT(DATE, create_date);", nativeQuery = true)
    List<Object[]> statisticRevenueDayInMonth(String month, String year);

    @Query(value = "SELECT MONTH(create_date) AS month, COALESCE(SUM(b.amount), 0) - COALESCE(SUM(br.return_money), 0) + COALESCE(SUM(rd.quantity_return * pd.price), 0) AS revenue\n" +
            "FROM bill b LEFT JOIN bill_return br ON b.id = br.bill_id LEFT JOIN return_detail rd ON br.id = rd.id\n" +
            "LEFT JOIN product_detail pd ON rd.product_detail_id = pd.id\n" +
            "WHERE YEAR(b.create_date) = :year and b.status='HOAN_THANH' \n" +
            "GROUP BY MONTH(b.create_date)\n" +
            "ORDER BY MONTH(b.create_date)", nativeQuery = true)
    List<Object[]> statisticRevenueMonthInYear(String year);

    @Query(value = "select b from Bill b where b.status = 'HOAN_THANH' AND b.create_Date between :fromDate and :toDate", nativeQuery = true)
    List<Bill> findAllCompletedByDate(@Param("fromDate") LocalDateTime fromDate,@Param("toDate") LocalDateTime toDate);

    @Query("select count(b) from Bill b where b.status='CHO_XAC_NHAN'")
    int getTotalBillStatusWaiting();

    @Query(value = "SELECT \n" +
            "FORMAT(b.create_date, 'MM-yyyy') AS date,\n" +
            "COALESCE(SUM(b.amount), 0) - COALESCE(SUM(br.return_money), 0) + COALESCE(SUM(rd.quantity_return * pd.price), 0) AS revenue\n" +
            "FROM bill b LEFT JOIN bill_return br ON b.id = br.bill_id LEFT JOIN return_detail rd ON br.id = rd.id\n" +
            "LEFT JOIN product_detail pd ON rd.product_detail_id = pd.id\n" +
            "WHERE b.status = 'HOAN_THANH' AND ( (b.create_date BETWEEN :fromDate AND :toDate)) \n" +
            "GROUP BY \n" +
            "FORMAT(b.create_date, 'MM-yyyy')\n" +
            "ORDER BY \n" +
            "FORMAT(b.create_date, 'MM-yyyy');", nativeQuery = true)
    List<Object[]> statisticRevenueFormMonth(String fromDate, String toDate);

    @Query(value = "SELECT CONVERT(varchar, b.create_date, 23) AS date, " +
            "COALESCE(SUM(b.amount), 0) - COALESCE(SUM(br.return_money), 0) + COALESCE(SUM(rd.quantity_return * pd.price), 0) AS revenue " +
            "FROM bill b LEFT JOIN bill_return br ON b.id = br.bill_id " +
            "LEFT JOIN return_detail rd ON br.id = rd.id " +
            "LEFT JOIN product_detail pd ON rd.product_detail_id = pd.id " +
            "WHERE b.status = 'HOAN_THANH' AND " +
            "b.create_date BETWEEN CONVERT(DATETIME, :fromDate, 120) AND CONVERT(DATETIME, :toDate, 120) " +
            "GROUP BY CONVERT(varchar, b.create_date, 23) " +
            "ORDER BY CONVERT(varchar, b.create_date, 23)", nativeQuery = true)
    List<Object[]> statisticRevenueDaily(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "select status, count(b.status) as quantity, sum(b.amount) as revenue from bill b group by b.status", nativeQuery = true)
    List<OrderStatistic> statisticOrder();

    @Query(value = "select b.code as billCode, b.id as billId, pm.order_id as orderId, c.name as customerName, b.update_date as cancelDate, b.amount as totalAmount, pm.status_exchange as statusExchange from bill b left join customer c on b.customer_id = c.id  left join payment pm on pm.bill_id = b.id \n" +
            "join payment_method pme on pme.id = b.payment_method_id where b.status='HUY' and pme.name='CHUYEN_KHOAN' order by b.update_date desc", nativeQuery = true)
    List<RefundDto> findListNeedRefund();

    @Query(value = "SELECT DISTINCT b.id AS maHoaDon,b.code AS maDinhDanh, a.name AS hoVaTen, a.phone_Number " +
            "AS soDienThoai,b.create_Date AS ngayTao, b.amount AS tongTien, b.status AS trangThai, b.invoice_Type " +
            "AS loaiDon, pm.name AS hinhThucThanhToan, coalesce(br.code, '') as maDoiTra, pmt.order_Id as maGiaoDich " +
            "FROM Bill b " +
            "JOIN Payment pmt on b.id = pmt.bill_id " +
            "LEFT JOIN Customer a ON b.customer_id = a.id " +
            "LEFT JOIN Bill_Detail bd ON b.id = bd.bill_id " +
            "LEFT JOIN Payment_Method pm ON b.payment_Method_id = pm.id LEFT JOIN Bill_Return br on b.id = br.bill_id",nativeQuery = true)
    List<BillDtoInterface> listBill();

    @Query(value = "SELECT DISTINCT b.id AS maHoaDon,b.code AS maDinhDanh, a.name AS hoVaTen, a.phone_Number " +
            "AS soDienThoai, b.create_Date AS ngayTao, b.amount AS tongTien, b.status AS trangThai, b.invoice_Type " +
            "AS loaiDon, pm.name AS hinhThucThanhToan, coalesce(br.code, '') as maDoiTra " +
            "FROM Bill b " +
            "LEFT JOIN Customer a ON b.customer_id = a.id " +
            "LEFT JOIN Bill_Detail bd ON b.id = bd.bill_id " +
            "LEFT JOIN Payment_Method pm ON b.payment_Method_id = pm.id left join Bill_Return br on b.id = br.bill_id " +
            "WHERE (:maDinhDanh IS NULL OR b.code LIKE CONCAT('%', :maDinhDanh, '%')) " +
            "AND (:ngayTaoStart IS NULL OR :ngayTaoEnd IS NULL OR (b.create_Date BETWEEN :ngayTaoStart AND :ngayTaoEnd)) " +
            "AND (:trangThai IS NULL OR b.status = :trangThai) " +
            "AND (:loaiDon IS NULL OR b.invoice_Type= :loaiDon) "+
            "AND (:soDienThoai IS NULL OR a.phone_Number IS NULL OR a.phone_Number LIKE CONCAT('%', :soDienThoai, '%')) "+
            "AND (:hoVaTen IS NULL OR a.name IS NULL OR a.name LIKE CONCAT('%', :hoVaTen, '%'))",nativeQuery = true)
    List<BillDtoInterface> listSearchBill( @Param("maDinhDanh") String maDinhDanh,
                                           @Param("ngayTaoStart") LocalDateTime ngayTaoStart,
                                           @Param("ngayTaoEnd") LocalDateTime ngayTaoEnd,
                                           @Param("trangThai") BillStatus trangThai,
                                           @Param("loaiDon") InvoiceType loaiDon,
                                           @Param("soDienThoai") String soDienThoai,
                                           @Param("hoVaTen") String hoVaTen);
}
