package com.example.laba4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LaptopServlet", value = "/laptop")
public class LaptopServlet extends HttpServlet {

    private static final String FILE_PATH = "C:\\Users\\Rikon\\Desktop\\OOP\\laba4\\src\\main\\java\\com\\example\\laba4\\laptop.json";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder jsonRequest = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка при чтении запроса");
            return;
        }

        Gson gson = new GsonBuilder().create();
        Laptop laptop = gson.fromJson(jsonRequest.toString(), Laptop.class);


        List<Laptop> laptops = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(FILE_PATH))) {
            Type listType = new TypeToken<ArrayList<Laptop>>() {
            }.getType();
            laptops = gson.fromJson(fileReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        laptops.add(laptop);

        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            gson.toJson(laptops, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при записи ноутбука в файл");
            return;
        }

        doGet(request, response);
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        List<Laptop> laptops = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            Type listType = new TypeToken<ArrayList<Laptop>>() {
            }.getType();
            laptops = gson.fromJson(reader, listType);
            response.getWriter().write(gson.toJson(laptops));
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при чтении списка ноутбуков");
        }
    }



}
