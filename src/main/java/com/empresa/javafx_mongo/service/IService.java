package com.empresa.javafx_mongo.service;

import com.empresa.javafx_mongo.model.Datos;
import javafx.collections.ObservableList;

public interface IService {
    ObservableList<Datos> getDatos();
}