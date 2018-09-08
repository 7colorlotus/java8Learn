package com.lotus.java8Learn.refactorDesignModel.visitorModel;

interface Subject {
    void registerObserver(Observer o);

    void notifyObservers(String tweet);
}