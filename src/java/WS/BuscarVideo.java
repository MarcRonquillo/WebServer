/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import hibernate.util.HibernateUtil;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import model.Videos;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marc
 */
@WebService(serviceName = "BuscarVideo")
@Stateless()
public class BuscarVideo {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "buscarPorAutor")
    public java.util.List<model.Videos> buscarPorAutor(@WebParam(name = "autor") String autor) {
       List<Videos> result = new ArrayList<Videos>();
        Session session = HibernateUtil.openSession();
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Videos.class);
            
            if (!autor.equals("")) {
                criteria.add(Restrictions.ilike("autor", "%" + autor + "%"));
            }
            result = criteria.list();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "buscarVideos")
    public java.util.List<model.Videos> buscarVideos(@WebParam(name = "titulo") String titulo, @WebParam(name = "autor") String autor, @WebParam(name = "fecha") String fecha) {

        java.util.Date fechaCreacion = null;
        java.sql.Date fechaCreacionSql = null;
        if (fecha != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            try {
                fechaCreacion = (Date) df.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(BuscarVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
            fechaCreacionSql = new java.sql.Date(fechaCreacion.getTime());
        }
        
        
        List<Videos> result = new ArrayList<Videos>();
        Session session = HibernateUtil.openSession();
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Videos.class);
            
            if (!titulo.equals("")) {
                criteria.add(Restrictions.ilike("titulo", "%" + titulo + "%"));
            }
            if (!autor.equals("")) {
                criteria.add(Restrictions.ilike("autor", "%" + autor + "%"));
            }
            if (fechaCreacionSql != null) {
                criteria.add(Restrictions.eq("fechaCreacion", fechaCreacionSql));
            }
            result = criteria.list();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            session.close();
        }

        return result;
    }
}




