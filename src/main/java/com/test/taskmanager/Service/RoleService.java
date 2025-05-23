package com.test.taskmanager.Service;

import com.test.taskmanager.Model.Role;
import com.test.taskmanager.Repository.RoleRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class RoleService implements RoleRepository {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRoleId(Long roleId) {
        return roleRepository.findByRoleId(roleId);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Role> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Role> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Role> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> Longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Role getOne(Long Long) {
        return null;
    }

    @Override
    public Role getById(Long Long) {
        return null;
    }

    @Override
    public Role getReferenceById(Long Long) {
        return null;
    }

    @Override
    public <S extends Role> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Role> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Role> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Role> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Role> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Role, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Role> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Role> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Role> findById(Long Long) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long Long) {
        return false;
    }

    @Override
    public List<Role> findAll() {
        return List.of();
    }

    @Override
    public List<Role> findAllById(Iterable<Long> Longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long Long) {

    }

    @Override
    public void delete(Role entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> Longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Role> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Role> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return null;
    }
}
