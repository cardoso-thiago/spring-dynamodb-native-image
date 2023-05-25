package br.com.cardoso.service;

import br.com.cardoso.entity.ArtistDetails;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DynamoDBService {

    private final DynamoDbTemplate dynamoDbTemplate;

    public DynamoDBService(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    public ArtistDetails saveData(ArtistDetails artistDetails) {

        return dynamoDbTemplate.save(artistDetails);
    }

    public ArtistDetails updateData(ArtistDetails artistDetails) {

        return dynamoDbTemplate.update(artistDetails);
    }

    public void deleteByObject(ArtistDetails artistDetails) {

        dynamoDbTemplate.delete(artistDetails);
    }

    public void deleteById(String id) {

        Key key = Key.builder().partitionValue(id).build();
        dynamoDbTemplate.delete(key, ArtistDetails.class);
    }

    public ArtistDetails findById(String hashKey) {

        Key key = Key.builder().partitionValue(hashKey).build();
        return dynamoDbTemplate.load(key, ArtistDetails.class);
    }

    public List<ArtistDetails> scanDataByGenre(String genre) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":val1", AttributeValue.fromS(genre));

        Expression filterExpression = Expression.builder().expression("genre = :val1").expressionValues(expressionValues).build();

        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder().filterExpression(filterExpression).build();

        PageIterable<ArtistDetails> returnedList = dynamoDbTemplate.scan(scanEnhancedRequest, ArtistDetails.class);

        return returnedList.items().stream().collect(Collectors.toList());
    }
}