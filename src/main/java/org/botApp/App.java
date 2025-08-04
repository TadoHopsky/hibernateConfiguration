package org.botApp;

import org.botApp.Model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main( String[] args ) {
        Configuration configurationHibernate = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sessionFactory = configurationHibernate.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // Добавление пользователя в БД
        session.beginTransaction();
        session.persist(new Person("Tado Hopsky", 25));
        session.persist(new Person("Kseniya Frolova", 27));
        session.persist(new Person("Daniil Ostroverkh", 25));
        session.getTransaction().commit();

        // Получение пользователя с ID = 1
        session.beginTransaction();
        Person getPerson = session.find(Person.class, 1);
        System.out.println(getPerson);
        session.getTransaction().commit();

        session.close();
    }
}
