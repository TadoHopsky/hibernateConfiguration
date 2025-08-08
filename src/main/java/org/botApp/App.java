package org.botApp;

import org.botApp.Model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {
    public static void main( String[] args ) {
        Configuration configurationHibernate = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sessionFactory = configurationHibernate.buildSessionFactory();

        // Добавление пользователя в БД
        try (Session sessionAppend = sessionFactory.openSession()) {
            sessionAppend.beginTransaction();
            sessionAppend.persist(new Person("Tado Hopsky", 25));
            sessionAppend.persist(new Person("Kseniya Frolova", 27));
            sessionAppend.persist(new Person("Daniil Ostroverkh", 25));
            sessionAppend.getTransaction().commit();
        }

        // Получение пользователя с ID = 1
        try (Session sessionGetUserWithID = sessionFactory.openSession()) {
            sessionGetUserWithID.beginTransaction();
            Person getPerson = sessionGetUserWithID.find(Person.class, 1);
            System.out.println(getPerson);
            sessionGetUserWithID.getTransaction().commit();
        }

        // Обновление данных пользователя с ID = 1
        try (Session sessionUpdate = sessionFactory.openSession()) {
            sessionUpdate.beginTransaction();
            try{
                Person getPersonForUpdate = sessionUpdate.find(Person.class, 2);
                getPersonForUpdate.setName("NEW NAME");
                getPersonForUpdate.setAge(99);
                sessionUpdate.getTransaction().commit();
            }catch (Exception e){
                sessionUpdate.getTransaction().rollback();
                System.out.println("Пользователя с таким ID не существует :(");
            }
        }

        // Удаление пользователя с ID = 1
        try (Session sessionDelete = sessionFactory.openSession()) {
            sessionDelete.beginTransaction();
            Person personForDelete = sessionDelete.find(Person.class, 1);
            sessionDelete.remove(personForDelete);
            sessionDelete.flush();
            sessionDelete.getTransaction().commit();
        }

        /*
        =============================================================
        ========================= HQL ===============================
        =============================================================
        */

        // Получение всех пользователей HQL запрос [" FROM PERSON "]
        try (Session sessionGetAllHQL = sessionFactory.openSession()) {
            sessionGetAllHQL.beginTransaction();
            List<Person> personsList = sessionGetAllHQL.createQuery("from Person").getResultList();
            for (Person person : personsList) {
                System.out.println(person);
            }
        }

    }
}
