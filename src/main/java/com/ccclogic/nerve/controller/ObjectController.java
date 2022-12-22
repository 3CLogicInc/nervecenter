package com.ccclogic.nerve.controller;

import com.ccclogic.nerve.entities.common.EntitySerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.jibx.schema.codegen.extend.DefaultNameConverter;
import org.jibx.schema.codegen.extend.NameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.support.DefaultRepositoryInvokerFactory;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.repository.support.RepositoryInvoker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/basic/{repositoryName}")
public class ObjectController {

    @Autowired
    Repositories repositories;

    @Autowired
    ConversionService mvcConversionService;

    DefaultRepositoryInvokerFactory defaultRepositoryInvokerFactory;

    NameConverter nameConverter = new DefaultNameConverter();

    @PostConstruct
    public void init(){
        defaultRepositoryInvokerFactory = new DefaultRepositoryInvokerFactory(repositories, mvcConversionService);
    }

    @GetMapping
    public List getAll(@PathVariable String repositoryName){
        JpaRepository repository = (JpaRepository) getByName(repositoryName);
        return repository.findAll();
    }

    @GetMapping("/{entityId}")
    public ResponseEntity<Object> getById(@PathVariable String repositoryName, @PathVariable Integer entityId){
        JpaRepository repository = (JpaRepository) getByName(repositoryName);
        Optional<Object> result =  repository.findById(entityId);
        if(result.isEmpty())  return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{entityId}")
    public ResponseEntity deleteById(@PathVariable String repositoryName, @PathVariable Integer entityId){
        JpaRepository repository = (JpaRepository) getByName(repositoryName);
        repository.deleteById(entityId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Object data, @PathVariable String repositoryName){
        RepositoryInvoker ri = defaultRepositoryInvokerFactory.getInvokerFor(getClassByClassName(repositoryName));
        Object obj =new ObjectMapper().convertValue(data, getClassByClassName(repositoryName));
        Object saved = ri.invokeSave(obj);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{entityId}")
    public ResponseEntity<Object> save(@RequestBody Object data, @PathVariable String repositoryName, @PathVariable Integer entityId){
        RepositoryInvoker ri = defaultRepositoryInvokerFactory.getInvokerFor(getClassByClassName(repositoryName));
        Object obj =new ObjectMapper().convertValue(data, getClassByClassName(repositoryName));
        ((EntitySerializable)obj).setId(entityId);
        Object saved = ri.invokeSave(obj);
        return ResponseEntity.ok(saved);
    }


    @SneakyThrows
    private Repository getByName(String entityName){
        Class clazz = Class.forName(prefixName(entityName));
        Optional<Object> repository = repositories.getRepositoryFor(clazz);
        if(repository.isPresent()){
            return (CrudRepository) repository.get();
        }

        return null;
    }

    @SneakyThrows
    private Class getClassByClassName(String className){
        return ClassUtils.getClass(prefixName(className));
    }

    private String prefixName(String entityName){
        return "com.ccclogic.nerve.entities.webastra."+StringUtils.capitalize(nameConverter.depluralize(entityName));
    }

}
