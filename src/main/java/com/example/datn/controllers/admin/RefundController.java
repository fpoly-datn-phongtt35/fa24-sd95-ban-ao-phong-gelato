package com.example.datn.controllers.admin;
import com.example.datn.dto.Refund.SearchRefundDto;
import com.example.datn.entities.Payment;
import com.example.datn.repositories.BillRepository;
import com.example.datn.repositories.BillReturnRepository;
import com.example.datn.repositories.PaymentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RefundController {
    private final BillReturnRepository billReturnRepository;

    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;

    public RefundController(BillReturnRepository billReturnRepository, BillRepository billRepository, PaymentRepository paymentRepository) {
        this.billReturnRepository = billReturnRepository;
        this.billRepository = billRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/admin-only/need-refund-mng")
    public String viewRefundPage(SearchRefundDto searchRefundDto, Model model) {
        model.addAttribute("refundList", billRepository.findListNeedRefund());
        return "/admin/refund-mng";
    }

    @PostMapping("/admin/confirm-refund/{id}")
    public String confirmRefund(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Payment payment = paymentRepository.findByOrderId(id);
        payment.setStatusExchange(1);
        paymentRepository.save(payment);
        redirectAttributes.addFlashAttribute("successMessage", "Xác nhận hoàn " + payment.getAmount() + " cho mã giao dịch " + payment.getOrderId() + " thành công");
        return "redirect:/admin-only/need-refund-mng";
    }
}
