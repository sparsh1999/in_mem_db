
1. In memory search engine
   Your organization has started a new tech blog with interesting tech stories and you're responsible for designing and implementing an in-memory search engine. =[Supporting the search functionality on the blog content.

It should support the following.
It should be possible to create a dataset in the search engine.
It should be possible to insert documents into a given dataset. Each document is simply a piece of text.
It should be possible to search through documents for a search pattern in a given dataset.
It should be possible to order the search results.

Examples:
1)Search term: apple
Doc1: apple is a fruit
Doc2: apple apple come on
Doc3: oranges are sour
Doc4: apple is sweet
Doc5: veggies are healthy
Result : Doc2, Doc1, Doc4

models 
    Storage , addDocument(), getDocument() 
    MapBasedStorage(documentId)

    Document (id, String content, documentMetadata (like popularity, freshness etc))

    DataSet , contains a storage, which exposes to add or get the document
    addDocument()

    ReverseIndex
    Map<String, DocumentId>

    processDocument(document)
        for each word in document, update the index

Service 
    SearchEngineService 
        addDocument 
            addToStorage 
            update reverseIndex by calling procesDocument 

        searchKeyword(String keyword)
            getAllDocumentsId from reverse index regarding this keyword 
            fetch metdata for these documents
            strategy to order the documents based on some criteria 

Strategy 
    interface
        List<Documents sortDouments(List<Documents))
    defaultStrategy 
        sort based on documentId or insertionTime

    