/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModel.ChiTietHoaDonBan;
import Utilities.DBConnection;
import ViewModel.CTHDBanViewModel;
import ViewModel.HDBanViewModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class ChiTietHoaDonRepository {
    DBConnection db;
    ResultSet rs = null;
    Statement st = null;
    PreparedStatement pst = null;
    List<ChiTietHoaDonBan> ListchitietHoaDon = null;
    List<HDBanViewModel> listViewMD = null;
    List<CTHDBanViewModel> listCTBvmd = null;
    public ChiTietHoaDonRepository() {
    }
    
    public String insert (ChiTietHoaDonBan cthd){
        String insert =" INSERT INTO dbo.CTHoaDonBan\n" +
"							 (\n" +
"							     IdHoaDonBan,\n" +
"							     IdCTSach,\n" +
"							     SoLuong,\n" +
"							     DonGia\n" +
"							 )\n" +
"							 VALUES\n" +
"							 (  (SELECT TOP 1 IdHoaDonBan FROM dbo.HoaDonBan ORDER BY IdHoaDonBan DESC),?,?,?)"; 
        try {
            pst = db.getConnection().prepareStatement(insert);
           pst.setInt(1, cthd.getIdChiTietSach());
           pst.setInt(2, cthd.getSoLuong());
           pst.setFloat(3, cthd.getDonGia());
            pst.executeUpdate();
            return "them thanh cong";
        } catch (Exception e) {
            
        }
        return "Them khong thanh cong";
    }
    
     public List<HDBanViewModel> getAll() {

        String sql = "   SELECT * FROM dbo.HoaDonBan JOIN dbo.Users \n" +
" ON  Users.IdUsers = HoaDonBan.IdUsers JOIN dbo.KhachHang \n" +
" ON KhachHang.IdKhachHang = HoaDonBan.IdKhachHang";
        try {
            st = db.getConnection().createStatement();
            rs = st.executeQuery(sql);
            listViewMD = new ArrayList<>();
            while (rs.next()) {
                HDBanViewModel i = new HDBanViewModel();
                i.setIdHoaDonBan(rs.getInt("IdHoaDonBan"));
                i.setIdKhachHang(rs.getInt("IdKhachHang"));
                i.setIdUsers(rs.getInt("IdUsers"));
                i.setIdVoucher(rs.getInt("IdVoucher"));
                i.setNGAYTHANHTOAN(rs.getString("NGAYTHANHTOAN"));
                i.setGhiChu(rs.getString("GhiChu"));
                i.setStatusPay(rs.getBoolean("statusPay"));
                i.setStatusInvoice(rs.getBoolean("statusInvoice"));
                i.setTenKhachHang(rs.getString(26));
                i.setTenUser(rs.getString(16));
                i.setTongTien(rs.getDouble("TongTien"));
                i.setTienKhachDua(rs.getDouble("TienKhachDua"));
                i.setTienTraLai(rs.getDouble("TienTraLai"));
                listViewMD.add(i);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(NCCRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listViewMD;
    }
     
     public List<CTHDBanViewModel> selectByIdBan(int id){
         String sql =" SELECT B.IdCTHoaDonBan,S.TenSach,K.Hoten,t.TenTacGia,N.TenNgonNGu,X.TenNXB,B.SoLuong,B.DonGia\n" +
" FROM dbo.CTHoaDonBan B\n" +
" JOIN dbo.HoaDonBan H ON H.IdHoaDonBan = B.IdHoaDonBan\n" +
" JOIN dbo.KhachHang K ON K.IdKhachHang = H.IdKhachHang\n" +
" JOIN dbo.ChiTietSach C ON C.IdCTSach = B.IdCTSach\n" +
" JOIN dbo.Sach S ON S.IdSach = C.IdSach\n" +
" JOIN dbo.TacGia T ON T.IdTacGia = C.IdTacGia\n" +
" JOIN dbo.NgonNgu N ON N.IdNgonNgu = C.IdNgonNgu\n" +
" JOIN dbo.NXB X ON X.IdNXB = C.IdNXB\n" +
" WHERE B.IdHoaDonBan = ?";
           try {
            //st = db.getConnection().createStatement();
            pst = db.getConnection().prepareStatement(sql);
           pst.setInt(1, id);
           rs = pst.executeQuery();
            listCTBvmd= new ArrayList<>();
            while (rs.next()) {
               CTHDBanViewModel de = new CTHDBanViewModel();
               de.setIdCTHoaDonBan(rs.getInt("IdCTHoaDonBan"));
                de.setSoLuong(rs.getInt("SoLuong"));
                de.setDonGia(rs.getInt("DonGia"));
                de.setTenNXB(rs.getString("TenNXB"));
                de.setTenNgonNGu(rs.getString("TenNgonNGu"));
                de.setTenTacGia(rs.getString("TenTacGia"));
                de.setTenSach(rs.getString("TenSach"));
                de.setTenKhachHang(rs.getString("Hoten"));
                
                listCTBvmd.add(de);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(NCCRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCTBvmd;
     }
}
