package at.htl.ettinger.unit;

import at.htl.ettinger.schoolclass.SchoolClass;
import at.htl.ettinger.teacher.Teacher;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Unit extends PanacheEntity {
    @Column(precision = 1)
    public Integer day;
    @Column(precision = 2)
    public Integer unit;

    public String subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    public Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "schoolclass_id")
    public SchoolClass schoolClass;

    public Unit() {
    }

    public Unit(Integer day, Integer unit, String subject, Teacher teacher, SchoolClass schoolClass) {
        this.day = day;
        this.unit = unit;
        this.subject = subject;
        this.teacher = teacher;
        this.schoolClass = schoolClass;
    }

    public Unit(Integer day, Integer unit, String subject) {
        this.day = day;
        this.unit = unit;
        this.subject = subject;
    }

    public UnitDTO toDTO() {
        return new UnitDTO(id, day, unit, subject, schoolClass.id, teacher.id);
    }

    public static Unit fromDTO(UnitDTO u) {
        Unit unit = new Unit(u.day(), u.unit(), u.subject());
        if (u.id() != -1) {
            unit.id = u.id();
        }
        return unit;
    }
}
