package com.jkxy.car.api.service.Impl;

import com.jkxy.car.api.dao.CarDao;
import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("carService")
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Override
    public List<Car> findAll() {
        return carDao.findAll();
    }

    @Override
    public Car findById(int id) {
        return carDao.findById(id);
    }

    @Override
    public List<Car> findByCarName(String carName) {
        return carDao.findByCarName(carName);
    }

    @Override
    public void deleteById(int id) {
        carDao.deleteById(id);
    }

    @Override
    public void updateById(Car car) {
        carDao.updateById(car);
    }

    @Override
    public void insertCar(Car car) {
        carDao.insertCar(car);
    }

    @Override
    public boolean buyCar(int id ,int carNum){
        Car temp = carDao.findById(id);
        if (temp.getCarNum()<carNum){
            System.out.println("库存不足，无法购买");
            return false;
        }else{
            ReentrantLock lock = new ReentrantLock();
            temp.setCarNum(temp.getCarNum()-carNum);
            lock.lock();
            carDao.updateById(temp);
            lock.unlock();
            System.out.println("购买成功");
            System.out.println("当前车数"+temp.getCarNum());
        }
        return true;
    }

    @Override
    public List<Car> fuzzyFindByCarName(String carName, Long start, Long end) {
        return carDao.fuzzyFindByCarName(carName, start, end);
    }
}
