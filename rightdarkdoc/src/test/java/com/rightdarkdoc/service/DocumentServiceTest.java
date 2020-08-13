package com.rightdarkdoc.service;


import com.rightdarkdoc.entity.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DocumentServiceTest {


    @Autowired
    private DocumentService documentService;

    @Test
    public void addDocumentTest(){
        Document document=  new Document();
        document.setContent("wowowowowowowowowowowowwowowowowowowowowowowowowow");
        document.setTitle("aaaaaa");
        document.setIstrash(0);
        document.setEditcount(1);
        documentService.addDocument(document);
    }


    @Test
    public void selectDocByDocIdTest(){
        System.out.println(documentService.selectDocByDocId(1));
    }

    @Test
    public void selectDocByCreatorIdTest(){
        List<Document> docs=documentService.selectDocByCreatorId(2);
        System.out.println(docs);
    }

    @Test
    public void updateDocTitleTest(){
        documentService.updateDocTitle(1,"wzkwzkwzk");
    }


    @Test
    public void updateDoc(){
        Document document=  new Document();
        document.setDocid(1);
        document.setContent("wasdkfhaisdhfkabsdfkjbasdfwowowowowowowowowowowowow");
        document.setTitle("xqyxqyxqy");
        document.setIstrash(0);
        document.setEditcount(100);
        documentService.updateDocument(document);
    }


    @Test
    public void deleDocTest(){
        documentService.deleteDoc(2);
    }



}
