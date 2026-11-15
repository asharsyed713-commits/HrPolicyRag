package com.hrpolicy.rag;


import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class DataLoader {

    private final VectorStore vectorStore;

    private final Resource policyFile;

    public DataLoader(VectorStore vectorStore,
                      @Value("classpath:/hr_policy_file.pdf") Resource policyFile){

        this.vectorStore = vectorStore;
        this.policyFile = policyFile;
    }

    //Step 1: Read Pdf
    public int loadDataInVectorDb(){
        TikaDocumentReader reader = new TikaDocumentReader(policyFile);
        List<Document> documents = reader.get();


        //Step 2: Split pdf text to chunks
        TokenTextSplitter textSplitter = TokenTextSplitter.builder()
                .withChunkSize(800)
                .withMaxNumChunks(10000)
                .build();

        List<Document> chunks = textSplitter.split(documents);

        //Step 3: Store chunks in Qdrant
        vectorStore.add(chunks);
        return chunks.size();

    }



}
