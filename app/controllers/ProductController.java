package controllers;

import entity.Smartphone;
import models.ProductRepository;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.edit;
import views.html.index;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class ProductController extends Controller {

    private final FormFactory formFactory;
    private final ProductRepository productRepository;
    private final HttpExecutionContext ec;

    @Inject
    public ProductController(FormFactory formFactory, ProductRepository productRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.productRepository = productRepository;
        this.ec = ec;
    }
    @Transactional
    public Result viewEdit(Long id) {
        Smartphone s = productRepository.getItem(id);
        System.out.println("smartphone.toString" + s.toString());
        System.out.println("smartphone.toString" + s.getReleaseDate());
        return ok(edit.render("Edit information about " + s.getBrand(), true, s));
    }
    @Transactional
    public Result viewAdd() {
        return ok(edit.render("Add new Smartphone", false, null));
    }
    @Transactional
    public CompletionStage<Result> deleteProduct(Long id) {
        return productRepository.delete(id).thenApplyAsync(p -> {
            return redirect(routes.ProductController.getProducts());
        }, ec.current());
    }
    @Transactional
    public CompletionStage<Result> editProduct(Long id) {
        Smartphone smartphone = formFactory.form(Smartphone.class).bindFromRequest().get();
        if (getDate() != null)
            smartphone.setReleaseDate(getDate());
        else
            smartphone.setReleaseDate(new Date());
        smartphone.setId(id);
        return productRepository.update(smartphone).thenApplyAsync(p -> {
            return redirect(routes.ProductController.getProducts());
        }, ec.current());
    }

    @Transactional
    public CompletionStage<Result> addProduct() {
        Smartphone smartphone = formFactory.form(Smartphone.class).bindFromRequest().get();
        if (getDate() != null)
            smartphone.setReleaseDate(getDate());
        else
            smartphone.setReleaseDate(new Date());
        System.out.println("smartphone.toString" + smartphone.toString());
        return productRepository.add(smartphone).thenApplyAsync(p -> {
            return redirect(routes.ProductController.getProducts());
        }, ec.current());
    }
    @Transactional
    public CompletionStage<Result> getProducts() {
        return productRepository.list().thenApplyAsync(personStream -> {
            return ok(index.render(personStream.collect(Collectors.toList())));
        }, ec.current());
    }

    private Date getDate() {
        String[] accepts = {"name", "date"};
        Form<Smartphone> form = formFactory.form(Smartphone.class).bindFromRequest(accepts);

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(form.field("date").value()));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}