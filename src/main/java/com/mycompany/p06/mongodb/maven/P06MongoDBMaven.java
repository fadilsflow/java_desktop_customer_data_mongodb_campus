/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.p06.mongodb.maven;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class P06MongoDBMaven {

    public static void main(String[] args) {

        // Membuat koneksi MongoDB
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("Sambat");
        MongoCollection<Document> makananCollection = database.getCollection("makanan");
        
        
        // Tampilkan semua makanan
        System.out.println("Semua Makanan:");
        tampilkanSemuaMakanan(makananCollection);
        
        // Cari makanan berdasarkan nama
        System.out.println("\nCari Makanan yang mengandung 'Nasi':");
        cariMakananByNama(makananCollection, "Nasi");
        
        // Update harga makanan
        updateMakanan(makananCollection, "1", "Nasi Goreng Spesial", 30000);
        
        // Hapus makanan berdasarkan id
        hapusMakanan(makananCollection, "2");
        
        // Tampilkan semua makanan setelah perubahan
        System.out.println("\nMakanan setelah perubahan:");
        tampilkanSemuaMakanan(makananCollection);
    }

    // Method untuk menambah makanan
    public static void tambahMakanan(MongoCollection<Document> collection, String id, String nama, double harga) {
        Document doc = new Document("id", id)
                .append("nama", nama)
                .append("harga", harga);
        collection.insertOne(doc);
    }

    // Method untuk menampilkan semua makanan
    public static void tampilkanSemuaMakanan(MongoCollection<Document> collection) {
        List<Document> makananList = collection.find().into(new java.util.ArrayList<>());
        for (Document doc : makananList) {
            System.out.println(doc.get("harga"));
        }
    }

    // Method untuk mencari makanan berdasarkan nama
    public static void cariMakananByNama(MongoCollection<Document> collection, String nama) {
        List<Document> makananList = collection.find(Filters.regex("nama", nama, "i")).into(new java.util.ArrayList<>());
        for (Document doc : makananList) {
            System.out.println(doc.toJson());
        }
    }

    // Method untuk mengupdate makanan
    public static void updateMakanan(MongoCollection<Document> collection, String id, String namaBaru, double hargaBaru) {
        collection.updateOne(Filters.eq("id", id), 
                Updates.combine(
                        Updates.set("nama", namaBaru),
                        Updates.set("harga", hargaBaru)
                ));
    }

    // Method untuk menghapus makanan
    public static void hapusMakanan(MongoCollection<Document> collection, String id) {
        collection.deleteOne(Filters.eq("id", id));
    }
}