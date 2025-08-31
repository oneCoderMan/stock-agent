package com.codersim.aggregate.vector.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 23:20
 * @Description
 *
 */
@Service
@Slf4j
public class MarkdownFileReaderDomainService {
    public List<Document> readMarkdownFile(String filePath) throws Exception {
        List<MarkdownDocumentReader> markdownDocumentReaderList = loadMarkdownDocuments(filePath);

        int size = 0;
        if (markdownDocumentReaderList.isEmpty()) {
            log.info("No markdown documents found in the directory.");
            return emptyList();
        }

        log.info("Start to load markdown documents ......");
        List<Document> allDocument = new ArrayList<>();
        for (MarkdownDocumentReader markdownDocumentReader : markdownDocumentReaderList) {
            List<Document> documents = new TokenTextSplitter(2000, 1024, 10, 10000, true)
                    .transform(markdownDocumentReader.get());
            size += documents.size();
            allDocument.addAll(documents);
        }
        log.info("Load markdown documents  successfully. Load {} documents.", size);
        return allDocument;
    }

    private List<MarkdownDocumentReader> loadMarkdownDocuments(String fileDirPath)
            throws IOException, URISyntaxException {
        List<MarkdownDocumentReader> readers;

        // 首先检查jar包当前运行目录是否存在markdown文件
        Path currentDirPath = Paths.get(fileDirPath);

        if (Files.exists(currentDirPath) && Files.isDirectory(currentDirPath)) {
            log.info("Found markdown directory in current running directory: {}", currentDirPath);

            try (Stream<Path> paths = Files.walk(currentDirPath)) {
                List<Path> markdownFiles = paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".md"))
                        .collect(Collectors.toList());

                if (!markdownFiles.isEmpty()) {
                    log.info("Loading {} markdown files from current directory", markdownFiles.size());
                    readers = markdownFiles.stream()
                            .map(path -> {
                                String filePath = path.toAbsolutePath().toString();
                                return new MarkdownDocumentReader("file:" + filePath);
                            })
                            .collect(Collectors.toList());
                    return readers;
                } else {
                    log.info("No markdown files found in current directory, falling back to resources");
                }
            }
        } else {
            log.info("Markdown directory not found in current directory, falling back to resources");
        }
        return emptyList();
    }

}
