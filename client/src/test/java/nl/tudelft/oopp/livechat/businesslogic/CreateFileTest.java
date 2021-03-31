package nl.tudelft.oopp.livechat.businesslogic;

import nl.tudelft.oopp.livechat.data.Lecture;
import nl.tudelft.oopp.livechat.data.Question;
import org.apache.commons.io.FileDeleteStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class CreateFileTest {
    private static final UUID lectureId = UUID.randomUUID();
    private static final UUID modkey = UUID.randomUUID();
    private static final String pathName = "questions/";
    private static CreateFile createFile;

    /**
     * A helper method to delete files and directory after each test to make them independent.
     * @throws IOException if something goes wrong
     */
    private void cleanup() throws IOException {
        File fin = new File(pathName);
        File[] files = fin.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            FileDeleteStrategy.FORCE.delete(file);
        }
        Files.deleteIfExists(Path.of(pathName));
    }

    @BeforeAll
    public static void setUp() {
        createFile = new CreateFile();
        Lecture.setCurrent(new Lecture(lectureId, modkey, "Linux","root"));
    }

    @Test
    public void invalidPathExceptionTest() {
        assertFalse(createFile.setPath("////asdfs"));
    }

    @Test
    public void createFileNoFileTest() throws IOException {
        Path path = Path.of(pathName);
        assertTrue(createFile.setPath(pathName));
        assertTrue(createFile.createFile());

        //check if the directory exists
        assertTrue(Files.exists(path));

        //check if the file is present
        Stream<Path> entries = Files.list(path);
        assertTrue(entries.findFirst().isPresent());

        cleanup();
    }

    @Test
    public void createFileNoFileNoLectureTest() throws IOException {
        Lecture.setCurrent(null);
        Path path = Path.of(pathName);
        createFile.setPath(pathName);

        assertTrue(createFile.createFile());

        //check if the directory exists
        assertTrue(Files.exists(path));

        //check if the file is present
        Stream<Path> entries = Files.list(path);
        assertTrue(entries.findFirst().isPresent());

        cleanup();
        Lecture.setCurrent(new Lecture(lectureId, modkey, "Linux","root"));
    }

    @Test
    public void createFileDirectoryExistsTest() throws IOException {
        Path path = Path.of(pathName);
        Files.createDirectory(path);
        createFile.setPath(pathName);

        assertTrue(createFile.createFile());

        //check if the directory exists
        assertTrue(Files.exists(path));

        //check if the file is present
        Stream<Path> entries = Files.list(path);
        assertTrue(entries.findFirst().isPresent());

        cleanup();
    }

    @Test
    public void createFileNoParentDirectoryTest() throws IOException {
        createFile.setPath("blob/" + pathName);
        assertFalse(createFile.createFile());

        cleanup();
        createFile.setPath(pathName);
    }

    @Test
    public void createFileFileExistsTest() throws IOException {
        createFile.setPath(pathName);
        createFile.createFile();
        assertTrue(createFile.createFile());

        cleanup();
    }

    @Test
    public void createFileExceptionTest() throws IOException {
        createFile.setPath("");
        assertFalse(createFile.createFile());

        cleanup();
        createFile.setPath(pathName);
    }

    @Test
    public void writeToFileNoFileTest() throws IOException {
        createFile.setPath(pathName);
        createFile.createFile();

        cleanup();
        assertFalse(createFile.writeToFile(new ArrayList<>()));
    }

    @Test
    public void writeToFileNullFileTest() {
        CreateFile cf = new CreateFile();
        assertFalse(cf.writeToFile(new ArrayList<>()));
    }

    @Test
    public void testHeaderTest() throws IOException {
        Path path = Path.of(pathName);
        createFile.setPath(pathName);
        createFile.createFile();

        createFile.writeToFile(new ArrayList<>());

        Stream<Path> entries = Files.list(path);
        Path p = entries.findFirst().orElse(null);
        if (p == null) {
            fail();
        }

        try (BufferedReader bufferedReader = new BufferedReader(
                            new FileReader(p.toString()))) {
            assertEquals("Lecture Name: \"" + Lecture.getCurrent().getName() + "\"",
                    bufferedReader.readLine());
            assertEquals("Responsible Lecturer: " + Lecture.getCurrent().getCreatorName(),
                    bufferedReader.readLine());

            //skip some data
            assertNotNull(bufferedReader.readLine());
            assertNotNull(bufferedReader.readLine());
            assertNotNull(bufferedReader.readLine());

            assertEquals("Number of questions: 0", bufferedReader.readLine());
            assertNotNull(bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        }

        cleanup();
    }

    @Test
    public void testNoAnswer() throws IOException {
        Path path = Path.of(pathName);
        createFile.setPath(pathName);
        createFile.createFile();

        Question q = new Question(lectureId, "First", 42);
        List<Question> qs = List.of(q);
        createFile.writeToFile(qs);

        Stream<Path> entries = Files.list(path);
        Path p = entries.findFirst().orElse(null);
        if (p == null) {
            fail();
        }

        try (BufferedReader bufferedReader = new BufferedReader(
                        new FileReader(p.toString()))) {
            //skip some data
            for (int i = 0; i < 5; i++) {
                bufferedReader.readLine();
            }

            assertEquals("Number of questions: 1", bufferedReader.readLine());
            bufferedReader.readLine();

            String expected = "Q: \"" + q.getText() + "\" asked on " + q.getTime();
            assertEquals(expected, bufferedReader.readLine());
            expected = "A: -> No answer available";
            assertEquals(expected, bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        }

        cleanup();
    }

    @Test
    public void testWithAnswer() throws IOException {
        final Path path = Path.of(pathName);
        createFile.setPath(pathName);
        createFile.createFile();

        Question q = new Question(lectureId, "Second", 69);
        final List<Question> qs = List.of(q);
        q.setAnswered(true);
        q.setAnswerText("42");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        q.setAnswerTime(time);

        createFile.writeToFile(qs);

        Stream<Path> entries = Files.list(path);
        Path p = entries.findFirst().orElse(null);
        if (p == null) {
            fail();
        }

        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(p.toString()))) {
            //skip some data
            for (int i = 0; i < 5; i++) {
                bufferedReader.readLine();
            }

            assertEquals("Number of questions: 1", bufferedReader.readLine());
            bufferedReader.readLine();

            String expected = "Q: \"" + q.getText() + "\" asked on " + q.getTime();
            assertEquals(expected, bufferedReader.readLine());
            expected = "A: -> \"" + q.getAnswerText() + "\" answered on "
                    + q.getAnswerTime();
            assertEquals(expected, bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        }

        cleanup();
    }

    @Test
    public void testMultipleAnswerOneAnsweredTest() throws IOException {
        createFile.setPath(pathName);
        createFile.createFile();

        Question q1 = new Question(lectureId, "First", 42);
        //q1.setVotes(42);
        q1.setAnswered(true);
        Question q2 = new Question(lectureId, "Second", 69);
        List<Question> qs = List.of(q2, q1);
        createFile.writeToFile(qs);

        Stream<Path> entries = Files.list(Path.of(pathName));
        Path p = entries.findFirst().orElse(null);
        if (p == null) {
            fail();
        }

        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(p.toString()))) {
            //skip some data
            for (int i = 0; i < 5; i++) {
                bufferedReader.readLine();
            }

            assertEquals("Number of questions: 2", bufferedReader.readLine());
            bufferedReader.readLine();

            String expected = "Q: \"" + q1.getText() + "\" asked on " + q1.getTime();
            assertEquals(expected, bufferedReader.readLine());
            bufferedReader.readLine(); //skip the answer
            assertEquals("--------------------------------------", bufferedReader.readLine());

            expected = "Q: \"" + q2.getText() + "\" asked on " + q2.getTime();
            assertEquals(expected, bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        }

        cleanup();
    }

    @Test
    public void testMultipleAnswerOneMoreVotesTest() throws IOException {
        createFile.setPath(pathName);
        createFile.createFile();

        Question q1 = new Question(lectureId, "First", 42);
        //q1.setVotes(42);
        q1.setVotes(42);
        Question q2 = new Question(lectureId, "Second", 69);
        List<Question> qs = List.of(q2, q1);
        createFile.writeToFile(qs);

        Path path = Path.of(pathName);
        Stream<Path> entries = Files.list(path);
        Path p = entries.findFirst().orElse(null);
        if (p == null) {
            fail();
        }

        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(p.toString()))) {
            //skip some data
            for (int i = 0; i < 5; i++) {
                bufferedReader.readLine();
            }

            assertEquals("Number of questions: 2", bufferedReader.readLine());
            bufferedReader.readLine();

            String expected = "Q: \"" + q1.getText() + "\" asked on " + q1.getTime();
            assertEquals(expected, bufferedReader.readLine());
            bufferedReader.readLine(); //skip the answer
            assertEquals("--------------------------------------", bufferedReader.readLine());

            expected = "Q: \"" + q2.getText() + "\" asked on " + q2.getTime();
            assertEquals(expected, bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        }

        cleanup();
    }

}
