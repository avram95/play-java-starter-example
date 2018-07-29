package models;

import com.google.inject.ImplementedBy;
import entity.Smartphone;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPARepository.class)
public interface ProductRepository {

    CompletionStage<Smartphone> add(Smartphone smartphone);

    CompletionStage<Stream<Smartphone>> list();

    CompletionStage<Smartphone> delete(Long id);

    Smartphone getItem(Long id);

    CompletionStage<Smartphone> update(Smartphone smartphone);
}
