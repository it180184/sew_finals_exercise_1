package at.htl.ettinger.schoolclass;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SchoolClassRepository implements PanacheRepositoryBase<SchoolClass, String> {
}
