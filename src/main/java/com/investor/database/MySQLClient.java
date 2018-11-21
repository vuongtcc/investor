package com.investor.database;

import com.investor.Main;
import com.investor.dto.NotiDTO;
import com.investor.dto.RuleDTO;
import com.investor.dto.TransDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySQLClient {
    private static final Logger logger = LogManager.getLogger(MySQLClient.class);

    public static List<RuleDTO> queryCodes() {
        List<RuleDTO> list = new ArrayList<RuleDTO>();
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            ps = con.prepareStatement("select s_code from tb_rules group by s_code;");
            rs = ps.executeQuery();
            while (rs.next()) {
                RuleDTO ruleDTO = new RuleDTO();
                ruleDTO.setCode(rs.getString("s_code"));
                list.add(ruleDTO);
            }
            closeDB(new Object[]{rs, ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    public static List<RuleDTO> queryRules() {
        List<RuleDTO> list = new ArrayList<RuleDTO>();
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            ps = con.prepareStatement("select * from tb_rules;");
            rs = ps.executeQuery();
            while (rs.next()) {
                RuleDTO ruleDTO = new RuleDTO();
                ruleDTO.setCode(rs.getString("s_code"));
                ruleDTO.setChange(rs.getString("n_change"));
                ruleDTO.setVolumn(rs.getString("n_volumn"));
                ruleDTO.setType(rs.getString("s_type"));
                list.add(ruleDTO);
            }
            closeDB(new Object[]{rs, ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    public static List<TransDTO> queryLast() {
        List<TransDTO> list = new ArrayList<TransDTO>();
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            ps = con.prepareStatement("select distinct tt.* from tb_transaction tt inner join(SELECT tbt.a, MAX(tm) AS tm FROM tb_transaction tbt inner join tb_rules tbr on tbt.a=tbr.s_code GROUP BY tbt.a) tma on tt.a=tma.a and tt.tm = tma.tm;");
            rs = ps.executeQuery();
            while (rs.next()) {
                TransDTO transDTO = new TransDTO();
                transDTO.setA(rs.getString("a"));
                transDTO.setB(rs.getString("b"));
                transDTO.setC(rs.getString("c"));
                transDTO.setD(rs.getString("d"));
                transDTO.setE(rs.getString("e"));
                transDTO.setF(rs.getString("f"));
                transDTO.setG(rs.getString("g"));
                transDTO.setH(rs.getString("h"));
                transDTO.setI(rs.getString("i"));
                transDTO.setK(rs.getString("k"));
                transDTO.setL(rs.getString("l"));
                transDTO.setM(rs.getString("m"));
                transDTO.setN(rs.getString("n"));
                transDTO.setO(rs.getString("o"));
                transDTO.setP(rs.getString("p"));
                transDTO.setP(rs.getString("q"));
                transDTO.setR(rs.getString("r"));
                transDTO.setS(rs.getString("s"));
                transDTO.setT(rs.getString("t"));
                transDTO.setU(rs.getString("u"));
                transDTO.setV(rs.getString("v"));
                transDTO.setW(rs.getString("w"));
                transDTO.setX(rs.getString("x"));
                transDTO.setY(rs.getString("y"));
                transDTO.setZ(rs.getString("z"));
                transDTO.setTm(rs.getString("tm"));
                transDTO.setTb(rs.getString("tb"));
                transDTO.setTs(rs.getString("ts"));
                list.add(transDTO);
            }
            closeDB(new Object[]{rs, ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    public static List<TransDTO> queryMin(Date from, Date to) {
        List<TransDTO> list = new ArrayList<TransDTO>();
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            ps = con.prepareStatement("select min(tt.l) l,tt.a from tb_transaction tt where tt.tm >=? and tt.tm <? group by tt.a;");
            ps.setTimestamp(1, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(to.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                TransDTO transDTO = new TransDTO();
                transDTO.setA(rs.getString("a"));
                transDTO.setL(rs.getString("l"));
                list.add(transDTO);
            }
            closeDB(new Object[]{rs, ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    public static List<TransDTO> queryMax(Date from, Date to) {
        List<TransDTO> list = new ArrayList<TransDTO>();
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            ps = con.prepareStatement("select max(tt.l) l,tt.a from tb_transaction tt where tt.tm >=? and tt.tm <? group by tt.a;");
            ps.setTimestamp(1, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(to.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                TransDTO transDTO = new TransDTO();
                transDTO.setA(rs.getString("a"));
                transDTO.setL(rs.getString("l"));
                list.add(transDTO);
            }
            closeDB(new Object[]{rs, ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }


    public static List<NotiDTO> queryNoti(String code, float cFrom, float cTo, String type, Date from, Date to) {
        logger.info("check code:" + code + ",cFrom:" + cFrom + ",cTo:" + cTo + ",type=" + type + ",from:" + from + ",to:" + to);
        List<NotiDTO> list = new ArrayList<NotiDTO>();
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            ps = con.prepareStatement("select * from tb_notification where a=? and c>=? and c <? and t=? and d >= ? and d < ?;");
            ps.setString(1, code);
            ps.setFloat(2, cFrom);
            ps.setFloat(3, cTo);
            ps.setString(4, type);
            ps.setTimestamp(5, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(6, new java.sql.Timestamp(to.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                NotiDTO notiDTO = new NotiDTO();
                notiDTO.setA(rs.getString("a"));
                notiDTO.setB(rs.getString("b"));
                notiDTO.setC(rs.getString("c"));
                notiDTO.setS(rs.getString("s"));
                notiDTO.setD(rs.getString("d"));
                list.add(notiDTO);
            }
            closeDB(new Object[]{rs, ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }


    public static List<TransDTO> queryTrans(String a, Date from, Date to) {
        List<TransDTO> list = new ArrayList<TransDTO>();
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            ps = con.prepareStatement("select * from tb_transaction where a=? and tm >=? and tm < ?;");
            ps.setString(1, a);
            ps.setTimestamp(2, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(3, new java.sql.Timestamp(to.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                TransDTO transDTO = new TransDTO();
                transDTO.setA(rs.getString("a"));
                transDTO.setB(rs.getString("b"));
                transDTO.setC(rs.getString("c"));
                transDTO.setD(rs.getString("d"));
                transDTO.setE(rs.getString("e"));
                transDTO.setF(rs.getString("f"));
                transDTO.setG(rs.getString("g"));
                transDTO.setH(rs.getString("h"));
                transDTO.setI(rs.getString("i"));
                transDTO.setK(rs.getString("k"));
                transDTO.setL(rs.getString("l"));
                transDTO.setM(rs.getString("m"));
                transDTO.setN(rs.getString("n"));
                transDTO.setO(rs.getString("o"));
                transDTO.setP(rs.getString("p"));
                transDTO.setP(rs.getString("q"));
                transDTO.setR(rs.getString("r"));
                transDTO.setS(rs.getString("s"));
                transDTO.setT(rs.getString("t"));
                transDTO.setU(rs.getString("u"));
                transDTO.setV(rs.getString("v"));
                transDTO.setW(rs.getString("w"));
                transDTO.setX(rs.getString("x"));
                transDTO.setY(rs.getString("y"));
                transDTO.setZ(rs.getString("z"));
                transDTO.setTm(rs.getString("tm"));
                transDTO.setTb(rs.getString("tb"));
                transDTO.setTs(rs.getString("ts"));
                list.add(transDTO);
            }
            closeDB(new Object[]{rs, ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    public static void insertTrans(List<TransDTO> list) {
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {


            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            for (int j = 0; j < list.size(); j++) {
                TransDTO txn = list.get(j);
                List<TransDTO> isExist = null;
                if (txn.getTm() != null) {
                    Date tm = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(txn.getTm());
                    isExist = checkTransExist(txn.getA(), tm);
                }
                if (isExist == null || isExist.size() == 0 && Double.parseDouble(txn.getL()) > 0) {
                    ps = con.prepareStatement("insert into tb_transaction(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, tm, tb, ts) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
                    ps.setString(1, txn.getA());
                    ps.setString(2, txn.getB());
                    ps.setString(3, txn.getC());
                    ps.setString(4, txn.getD());
                    ps.setString(5, txn.getE());
                    ps.setDouble(6, Double.parseDouble(txn.getF()) * 100);
                    ps.setString(7, txn.getG());
                    ps.setDouble(8, Double.parseDouble(txn.getH()) * 100);
                    ps.setString(9, txn.getI());
                    ps.setDouble(10, Double.parseDouble(txn.getJ()) * 100);
                    ps.setString(11, txn.getK());
                    ps.setString(12, txn.getL());
                    ps.setDouble(13, Double.parseDouble(txn.getM()) * 100);
                    ps.setDouble(14, Double.parseDouble(txn.getN()) * 100);
                    ps.setString(15, txn.getO());
                    ps.setDouble(16, Double.parseDouble(txn.getP()) * 100);
                    ps.setString(17, txn.getQ());
                    ps.setDouble(18, Double.parseDouble(txn.getR()) * 100);
                    ps.setString(19, txn.getS());
                    ps.setDouble(20, Double.parseDouble(txn.getT()) * 100);
                    ps.setString(21, txn.getU());
                    ps.setString(22, txn.getV());
                    ps.setString(23, txn.getW());
                    ps.setString(24, txn.getX());
                    ps.setString(25, txn.getY());
                    ps.setString(26, txn.getZ());
                    if (txn.getTm() != null) {
                        Date tm = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(txn.getTm());
                        ps.setTimestamp(27, new java.sql.Timestamp(tm.getTime()));
                    } else {
                        ps.setTimestamp(27, new java.sql.Timestamp((new java.util.Date().getTime())));
                    }
                    ps.setString(28, txn.getTb());
                    ps.setString(29, txn.getTs());
                    ps.executeUpdate();
                }
            }
            closeDB(new Object[]{ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static void insertNoti(String a, float c, String s, String b, String t, int vol) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            ps = con.prepareStatement("insert into tb_notification(a, c, s, b, d, t, v) values (?,?,?,?,?,?,?);");
            ps.setString(1, a);
            ps.setFloat(2, c);
            ps.setString(3, s);
            ps.setString(4, b);
            ps.setTimestamp(5, new java.sql.Timestamp((new java.util.Date().getTime())));
            ps.setString(6, t);
            ps.setInt(7, vol);
            ps.executeUpdate();
            closeDB(new Object[]{ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
    }


    public static List<TransDTO> checkTransExist(String code, java.util.Date date) {
        List<TransDTO> list = new ArrayList<TransDTO>();
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.p.getProperty("db.url"), Main.p.getProperty("db.user"), Main.p.getProperty("db.pw"));
            ps = con.prepareStatement(" select * from tb_transaction where a=? and tm=?;");
            ps.setString(1, code);
            ps.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                TransDTO transDTO = new TransDTO();
                transDTO.setA(rs.getString("a"));
                transDTO.setB(rs.getString("b"));
                list.add(transDTO);
            }
            closeDB(new Object[]{rs, ps, con});
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    public static void closeDB(Object[] obs) {
        for (int j = 0; j < obs.length; j++) {
            try {
                if (obs[j] != null && obs[j] instanceof ResultSet) {
                    ((ResultSet) obs[j]).close();
                } else if (obs[j] != null && obs[j] instanceof PreparedStatement) {
                    ((PreparedStatement) obs[j]).close();
                } else if (obs[j] != null && obs[j] instanceof Statement) {
                    ((Statement) obs[j]).close();
                } else if (obs[j] != null && obs[j] instanceof Connection) {
                    ((Connection) obs[j]).close();
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }


}
