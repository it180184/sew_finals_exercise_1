package at.htl.ettinger.unit;

import at.htl.ettinger.schoolclass.SchoolClass;
import at.htl.ettinger.schoolclass.SchoolClassService;
import at.htl.ettinger.teacher.Teacher;
import at.htl.ettinger.teacher.TeacherService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UnitService {

    @Inject
    UnitRepository unitRepository;

    public List<UnitDTO> getAllForClass(String classId) {
        return unitRepository.getAllForClass(classId)
                .stream()
                .map(Unit::toDTO)
                .collect(Collectors.toList());
    }

    public long postUnit(Unit u, Teacher t, SchoolClass sc) {
        u.schoolClass = sc;
        u.teacher = t;
        return unitRepository.save(u);
    }

    public void delete(long id) {
        unitRepository.delete(unitRepository.findById(id));
    }
}
