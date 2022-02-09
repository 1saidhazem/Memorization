package com.example.memorization.RoomDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.memorization.Adapters.ListenerIdCenterAndGroup;
import com.example.memorization.Adapters.onClickListenerCenterNameGroup;
import com.example.memorization.onClick;
import com.example.memorization.onClickShowMap;
import java.util.Date;
import java.util.List;

public class MyViewModel extends AndroidViewModel {
    private Repository repository;

    LiveData<List<User>> allUser;
    LiveData<List<Center>> allCenter;
    LiveData<List<Group>> allGroup;


    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allUser = repository.getAllUser();
        allCenter = repository.getAllCenter();
        allGroup = repository.getAllGroup();
    }

    // Insert
    public long insertUser(User users, onClick<User> listener) {
        repository.insertUser(users, listener);
        return users.getIdentificationNumber();
    }

    public void insertCenter(Center... centers) {
        repository.insertCenter(centers);
    }

    public void insertGroup(Group... groups) {
        repository.insertGroup(groups);
    }

    public void insertWallet(Wallet... wallets) {
        repository.insertWallet(wallets);
    }

    public void insertStudent(Student... students) {
        repository.insertStudent(students);
    }

    public void insertTask(Task... tasks) {
        repository.insertTask(tasks);
    }


    // Get
    public void getUserById(long id, onClick<User> listener) {
        repository.getUserById(id, listener);
    }

    public LiveData<List<User>> getAllUser() {
        return repository.getAllUser();
    }

    public LiveData<List<User>> getAllManager() {
        return repository.getAllManager();
    }

    public LiveData<List<User>> getAllSupervisor() {
        return repository.getAllSupervisor();
    }

    public LiveData<List<User>> getAllWallet() {
        return repository.getAllWallet();
    }

    public LiveData<List<User>> getAllStudent() {
        return repository.getAllStudent();
    }

    public LiveData<List<User>> getSupervisorById(long idSupervisor){
        return repository.getSupervisorById(idSupervisor);
    }

//    public Group getBestGroup(){
//        return repository.getBestGroup();
//    }

//    public Wallet getBestWallet(){
//        return repository.getBestWallet();
//    }

    public LiveData<List<User>> getWalletById(long idWallet){
        return repository.getWalletById(idWallet);
    }

    public LiveData<List<StudentCenterGroup>> getNames(){
        return repository.getNames();
    }

    public LiveData<List<Long>> getAllPhoneNumberStudent() {
        return repository.getAllPhoneNumberStudent();
    }

    public LiveData<List<Center>> getAllCenter() {
        return repository.getAllCenter();
    }

    public LiveData<List<Center>> getAllCenterManager(long idManager) {
        return repository.getAllCenterManager(idManager);
    }

    public LiveData<List<Center>> getCenterSupervisor(long idSupervisor){
        return repository.getCenterSupervisor(idSupervisor);
    }

    LiveData<List<Task>> SearchTask(long idStudent , Date from , Date to){
        return repository.SearchTask(idStudent, from, to);
    }

    public LiveData<List<Group>> getGroupSupervisor(long idSupervisor){
        return repository.getGroupSupervisor(idSupervisor);
    }

    public LiveData<List<User>> getStudentById(long idStudent){
        return repository.getStudentById(idStudent);
    }

    public LiveData<List<Group>> getGroupStudent(long idStudent){
        return repository.getGroupStudent(idStudent);
    }

//    void getInfoStudent(onClick<StudentUser> listener, long idStudent) {
//        repository.getInfoStudent(listener, idStudent);
//    }

//    public LiveData<List<CenterSupervisor>> getAllCenterSupervisor(long idSupervisor){
//        return repository.getAllCenterSupervisor(idSupervisor);
//    };

    public void getPasswordCurrentUser(long idUser, onClick<String> listener) {
        repository.getPasswordCurrentUser(idUser, listener);
    }

    public LiveData<List<Group>> getAllGroup() {
        return repository.getAllGroup();
    }

    public LiveData<List<Group>> getGroupInCenter(int idCenter){
        return repository.getGroupInCenter(idCenter);
    }

    public void getInfoGroupInCenter(onClick<CenterGroup> listener) {
        repository.getInfoGroupInCenter(listener);
    }

    public void getCenterNameGroup(int idCenter, onClickListenerCenterNameGroup listener) {
        repository.getCenterNameGroup(idCenter, listener);
    }

    public LiveData<List<Group>> getGroupById(int id) {
        return repository.getGroupById(id);
    }

    public LiveData<List<Wallet>> getWalletById(int idGroup) {
        return repository.getWalletById(idGroup);
    }

    public void getCountGroup(countGroupListener listener) {
        repository.getCountGroup(listener);
    }

    public void getCountStudent(onClick<Integer> listener){
        repository.getCountStudent(listener);
    }

    public LiveData<List<Center>> SearchCenter(String Query) {
        return repository.SearchCenter(Query);
    }

    public void getLatLong(int idCenter, onClickShowMap listener) {
        repository.getLatLong(idCenter, listener);
    }

    public void getInfoStudent(long idStudent, ListenerIdCenterAndGroup listener) {
        repository.getInfoStudent(listener, idStudent);
    }

    public LiveData<List<Message>> getAllMessage() {
        return repository.getAllMessage();
    }

    public void getNameStudent(long idStudent,onClick<String> listener){
        repository.getNameStudent(idStudent, listener);
    }

    public void getIdCenter(onClick<Integer> listener) {
        repository.getIdCenter(listener);
    }

    public void getIdCenterAndGroup(ReturnedIdCenterAndGroup listener) {
        repository.getIdCenterAndGroup(listener);
    }

    public LiveData<List<Task>> getAllTask() {
        return repository.getAllTask();
    }

    public LiveData<List<Task>> getTasksByIdStudent(long idStudent){
        return repository.getTasksByIdStudent(idStudent);
    }

    public LiveData<List<StudentGroup>> getAllStudentInGroup(int idGroup) {
        return repository.getAllStudentInGroup(idGroup);
    }

    public LiveData<List<WalletGroup>> getAllWalletInGroup(int groupId){
        return repository.getAllWalletInGroup(groupId);
    }

    public void getSenderName(long idSender, onClick<String> listener) {
        repository.getSenderName(idSender, listener);
    }

    ;


    // Update
    public void updateCenter(Center center) {
        repository.updateCenter(center);
    }

    public void updateGroup(Group group) {
        repository.updateGroup(group);
    }

    public void updatePassword(String newPass, long idUser) {
        repository.updatePassword(newPass, idUser);
    }


    // Delete
    public void deleteCenter(int idCenter) {
        repository.deleteCenter(idCenter);
    }

    public void deleteGroup(int idGroup) {
        repository.deleteGroup(idGroup);
    }


}
