package ru.ibs.testing.site.repos;

import org.springframework.data.repository.CrudRepository;
import ru.ibs.testing.site.dto.Test;

import java.util.List;

public interface TestRepo extends CrudRepository<Test, Long> {

    List<Test> findByName(String name);
    Test findById(int name);

}
