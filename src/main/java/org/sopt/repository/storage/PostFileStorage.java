package org.sopt.repository.storage;

import org.sopt.domain.Post;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostFileStorage implements FileStorage<Post>{

    private static final String FILE_NAME = "posts.txt";

    @Override
    public void save(List<Post> posts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Post post : posts) {
                writer.write(post.getId() + "," + post.getTitle() + "," + post.getCreatedAt() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Post> load() {
        List<Post> posts = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return posts;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                LocalDateTime createdAt = LocalDateTime.parse(parts[2]);


                posts.add(new Post(id, title,createdAt));
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 로딩 실패: " + e.getMessage(), e);
        }

        return posts;
    }
}
