package at.htl.ettinger.unit;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UnitRepository implements PanacheRepository<Unit> {

    public List<Unit> getAllForClass(String classId) {
        var q = getEntityManager().createQuery("select u from Unit u " +
                "where u.schoolClass.id = :classId", Unit.class);
        q.setParameter("classId", classId);
        return q.getResultList();
    }

    public long save(Unit u) {
        if (!isPersistent(u)) {
            persist(u);
        } else {
            u = getEntityManager().merge(u);
        }
        return u.id;
    }
}
