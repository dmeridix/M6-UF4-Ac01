package com.iticbcn.danimerida.DAO;

import com.iticbcn.danimerida.model.*;
import org.hibernate.SessionFactory;

public class HistoricDAO extends GenDAOImpl<Historic>{
    public HistoricDAO(SessionFactory sessionFactory) {
        super(sessionFactory,Historic.class);
    }
}
