
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModel.KhachHang;
import Utilities.DBConnection;
import ViewModel.MatHangViewModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class KhachHangRepository {

    DBConnection db;
    ResultSet rs = null;
    Statement st = null;
    PreparedStatement pst = null;
    List<KhachHang> ListKhachHang = null;

    public KhachHangRepository() {
    }

    public List<KhachHang> getListKhachHang() {
        String select = "SELECT * FROM dbo.KhachHang ORDER BY IdKhachHang DESC";
        ListKhachHang = new ArrayList<>();
        try {
            st = db.getConnection().createStatement();
            rs = st.executeQuery(select);
            while (rs.next()) {
                ListKhachHang.add(new KhachHang(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietSachRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListKhachHang;
    }

    public boolean them(KhachHang kh) {
        String query = "INSERT INTO [dbo].[KhachHang]\n"
                + "           ([Hoten]\n"
                + "           ,[NgaySinh]\n"
                + "           ,[GioiTinh]\n"
                + "           ,[Sdt]\n"
                + "           ,[DiaChi]\n"
                + "           ,[TrangThai])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?)";
        int check = 0;
        try ( Connection con = DBConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {

            ps.setObject(1, kh.getHoTen());
            ps.setObject(2, kh.getNgaySinh());
            ps.setObject(3, kh.isGioiTinh());
            ps.setObject(4, kh.getSoDienThoai());
            ps.setObject(5, kh.getDiaChi());
            ps.setObject(6, kh.isTrangThai());
    
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

}
