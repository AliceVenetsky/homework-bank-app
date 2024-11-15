package dev.venetsky.hibernate;

import dev.venetsky.model.Account;
import dev.venetsky.model.User;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration

                .addPackage("dev.venetsky")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Account.class)
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "root")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto","update")
                .setProperty("hibernate.current_session_context_class", "thread");

        return configuration.buildSessionFactory();
    }
}
