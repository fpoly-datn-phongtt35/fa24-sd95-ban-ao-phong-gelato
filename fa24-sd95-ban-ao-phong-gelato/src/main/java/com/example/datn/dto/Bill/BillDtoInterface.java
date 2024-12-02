package com.example.datn.dto.Bill;
import com.example.datn.entities.enumClass.BillStatus;
import com.example.datn.entities.enumClass.InvoiceType;

import java.time.LocalDateTime;
import java.util.Date;
public interface BillDtoInterface {
    Long getMaHoaDon();
    String getMaDinhDanh();
    String getHoVaTen();
    String getSoDienThoai();
    LocalDateTime getNgayTao();
    Double getTongTien();
    BillStatus getTrangThai();
    InvoiceType getLoaiDon();
    String getHinhThucThanhToan();
    String getMaGiaoDich();
    String getMaDoiTra();
}
