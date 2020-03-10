package io.david215.forum.thread;

import org.springframework.data.repository.CrudRepository;

public interface ThreadRepository extends CrudRepository<Thread, Long> {
    Iterable<Thread> findAllByOrderByTimeDesc();
}
