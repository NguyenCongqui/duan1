/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Services;

import DomainModel.KhachHang;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface KhachHangService {
    List<KhachHang> getlistKhachHang();
    String them(KhachHang kh);
}
