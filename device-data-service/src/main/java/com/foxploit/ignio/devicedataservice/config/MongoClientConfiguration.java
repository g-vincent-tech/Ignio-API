package com.foxploit.ignio.devicedataservice.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoClientConfiguration extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "Ignio-DeviceStore";
    }

    @Override
    public MongoClient mongoClient() {
//        return MongoClients.create("mongodb://localhost:27017");
        return MongoClients.create("mongodb://luk3Sky:mongoLuke@ignio-cluster-shard-00-00-iqx0b.mongodb.net:27017,ignio-cluster-shard-00-01-iqx0b.mongodb.net:27017,ignio-cluster-shard-00-02-iqx0b.mongodb.net:27017/test?ssl=true&replicaSet=Ignio-Cluster-shard-0&authSource=admin&retryWrites=true&w=majority");
    }

}
