package com.example.memorization.RoomDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.memorization.Adapters.ListenerIdCenterAndGroup;
import com.example.memorization.Adapters.onClickListenerCenterNameGroup;
import com.example.memorization.onClick;
import com.example.memorization.onClickShowMap;

import java.util.Date;
import java.util.List;

public class Repository {

    private UserDao userDao;
    private WalletDao walletDao;
    private StudentDao studentDao;
    private CenterDao centerDao;
    private GroupDao groupDao;
    private TaskDao taskDao;
    private MessageDao messageDao;

    public Repository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        userDao = db.UserDao();
        walletDao = db.walletDao();
        studentDao = db.studentDao();
        centerDao = db.centerDao();
        groupDao = db.groupDao();
        taskDao = db.taskDao();
        messageDao = db.messageDao();
    }

    // Insert
    public long insertUser(User user, onClick<User> listener) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(user);
                listener.onClickItem(user);
            }
        });
        return user.getIdentificationNumber();
    }

    public void insertWallet(Wallet... wallets) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                walletDao.insertWallet(wallets);
            }
        });
    }

    public void insertStudent(Student... students) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDao.insertStudent(students);
            }
        });
    }

    public void insertCenter(Center... centers) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                centerDao.insertCenter(centers);
            }
        });
    }

    public void insertGroup(Group... groups) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupDao.insertGroup(groups);
            }
        });
    }

    public void insertTask(Task... tasks) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insertTask(tasks);
            }
        });
    }

    public void insertMessage(Message... messages) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                messageDao.insertMessage(messages);
            }
        });
    }

    // Get

    public void getUserById(long id , onClick<User> listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                User user = userDao.getUserById(id);
                listener.onClickItem(user);
            }
        });
    }

    public LiveData<List<User>> getAllUser() {
        return userDao.getAllUser();
    }

    public LiveData<List<User>> getAllManager() {
        return userDao.getAllManager();
    }

    public LiveData<List<User>> getAllSupervisor() {
        return userDao.getAllSupervisor();
    }

    public LiveData<List<User>> getAllWallet() {
        return userDao.getAllWallet();
    }

    public LiveData<List<Center>> getAllCenter() {
        return centerDao.getAllCenter();
    }

    public LiveData<List<Center>> getAllCenterManager(long idManager) {
        return centerDao.getAllCenterManager(idManager);
    }

    public LiveData<List<Center>> getCenterSupervisor(long idSupervisor){
        return centerDao.getCenterSupervisor(idSupervisor);
    }

    public LiveData<List<Group>> getAllGroup() {
        return groupDao.getAllGroup();
    }

    public LiveData<List<Group>> getGroupInCenter(int idCenter){
        return groupDao.getGroupInCenter(idCenter);
    }

    public LiveData<List<Long>> getAllPhoneNumberStudent(){
        return userDao.getAllPhoneNumberStudent();
    }

    public LiveData<List<Group>> getGroupSupervisor(long idSupervisor){
        return groupDao.getGroupSupervisor(idSupervisor);
    }

    public LiveData<List<StudentCenterGroup>> getNames(){
        return studentDao.getNames();
    }

    public LiveData<List<Group>> getGroupStudent(long idStudent){
     return groupDao.getGroupStudent(idStudent);
    }

    public LiveData<List<User>> getStudentById(long idStudent){
        return userDao.getStudentById(idStudent);
    }

    public void getNameStudent(long idStudent,onClick<String> listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String nameStudent =studentDao.getNameStudent(idStudent);
                listener.onClickItem(nameStudent);
            }
        });
    }

    public void getPasswordCurrentUser(long idUser, onClick<String> listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String password =userDao.getPasswordCurrentUser(idUser);
                listener.onClickItem(password);
            }
        });
    }

    public void getInfoGroupInCenter(onClick<CenterGroup> listener) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CenterGroup info = groupDao.getInfoGroupInCenter();
                listener.onClickItem(info);
            }
        });
    }

    public void getCenterNameGroup(int idCenter, onClickListenerCenterNameGroup listener) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Group centerName = groupDao.getCenterNameGroup(idCenter);
                listener.onClick(centerName);
            }
        });
    }

    public LiveData<List<Wallet>> getWalletById(int idGroup) {
        return walletDao.getWalletById(idGroup);
    }

    void getInfoStudent(ListenerIdCenterAndGroup listener , long idStudent){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Student student = userDao.getInfoStudent(idStudent);
                listener.onReturned(student.getIdCenter(),student.getIdGroup());
            }
        });
    }

    void getIdCenter(onClick<Integer> listener) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int idCenter = centerDao.getIdCenter();
                listener.onClickItem(idCenter);
            }
        });
    }

    void getIdCenterAndGroup(ReturnedIdCenterAndGroup listener) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                IdCenterAndGroup idCenterAndGroup = groupDao.getIdCenterAndGroup();
                listener.onReturnedIdCenterAndGroup(idCenterAndGroup.getIdCenter(), idCenterAndGroup.getIdCenter());
            }
        });
    }

    LiveData<List<User>> getSupervisorById(long idSupervisor){
        return userDao.getSupervisorById(idSupervisor);
    }

    LiveData<List<User>> getWalletById(long idWallet){
        return userDao.getWalletById(idWallet);
    }

    public LiveData<List<StudentGroup>> getAllStudentInGroup(int idGroup){
        return studentDao.getAllStudentInGroup(idGroup);
    }

//    public Wallet getBestWallet(){
//        return walletDao.getBestWallet();
//    }

    public LiveData<List<WalletGroup>> getAllWalletInGroup(int groupId){
        return walletDao.getAllWalletInGroup(groupId);
    }

    public LiveData<List<Task>> getAllTask() {
        return taskDao.getAllTask();
    }

    public LiveData<List<User>> getAllStudent(){
        return studentDao.getAllStudent();
    }

    LiveData<List<Task>> getTasksByIdStudent(long idStudent){
        return taskDao.getTasksByIdStudent(idStudent);
    }

    LiveData<List<Task>> SearchTask(long idStudent , Date from , Date to){
     return taskDao.SearchTask(idStudent, from, to);
    }

    public LiveData<List<Message>> getAllMessage() {
        return messageDao.getAllMessage();
    }

    public LiveData<List<Group>> getGroupById(int id) {
        return groupDao.getAllGroupById(id);
    }

    public void getCountGroup(countGroupListener listener) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int countGroup = groupDao.getCountGroup();
                listener.valueCountGroup(countGroup);
            }
        });
    }

//    public Group getBestGroup(){
//        return groupDao.getBestGroup();
//    }

    public void getCountStudent(onClick<Integer> listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int countStudent = userDao.getCountStudent();
                listener.onClickItem(countStudent);
            }
        });
    }

    public LiveData<List<Center>> SearchCenter(String Query) {
        return centerDao.SearchCenter(Query);
    }

    public void getLatLong(int idCenter, onClickShowMap listener) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Center center = centerDao.getLatLong(idCenter);
                listener.onReturnedCenter(center);
            }
        });
    }

    public void getSenderName(long idSender,onClick<String> listener){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String senderName = userDao.getSenderName(idSender);
                listener.onClickItem(senderName);
            }
        });
    };



    // Update
    public void updateCenter(Center center) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                centerDao.updateCenter(center);
            }
        });
    }

    public void updateGroup(Group group) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupDao.updateGroup(group);
            }
        });
    }

    public void updatePassword(String newPass,long idUser){
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.updatePassword(newPass, idUser);
            }
        });
    };



    // Delete
    public void deleteCenter(int idCenter) {
        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                centerDao.deleteCenter(idCenter);
            }
        });
    }

    public void deleteGroup(int idGroup) {

        MyDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupDao.deleteGroup(idGroup);
            }
        });
    }

}
