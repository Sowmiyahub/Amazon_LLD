
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

// Strategy interface: all filters follow the same contract
interface SearchFilter {
    boolean matches(Path path) throws IOException;
}

// Represents the type of entry to search for
enum EntryType {
    FILE,
    DIRECTORY,
    ANY
}

// Concrete Strategy: filters based on file or directory type
class TypeFilter implements SearchFilter {

    private final EntryType type;

    public TypeFilter(EntryType type) {
        this.type = type;
    }

    @Override
    public boolean matches(Path path) {
        switch (type) {
            case FILE:
                return Files.isRegularFile(path);

            case DIRECTORY:
                return Files.isDirectory(path);

            case ANY:
                return true;

            default:
                return false;
        }
    }
}

// Concrete Strategy: filters based on file or directory name
class NameFilter implements SearchFilter {

    private final String name;

    public NameFilter(String name) {
        this.name = name;
    }

    @Override
    public boolean matches(Path path) {
        Path fileName = path.getFileName();

        return fileName != null &&
               fileName.toString().contains(name);
    }
}

// Concrete Strategy: filters based on file extension
class ExtensionFilter implements SearchFilter {

    private final String extension;

    public ExtensionFilter(String extension) {
        this.extension = extension.startsWith(".")
                ? extension
                : "." + extension;
    }

    @Override
    public boolean matches(Path path) {
        Path fileName = path.getFileName();

        return Files.isRegularFile(path) &&
               fileName != null &&
               fileName.toString().endsWith(extension);
    }
}

// Concrete Strategy: filters files based on minimum size
class SizeFilter implements SearchFilter {

    private final long minSizeBytes;

    public SizeFilter(long minSizeBytes) {
        this.minSizeBytes = minSizeBytes;
    }

    @Override
    public boolean matches(Path path) throws IOException {
        return Files.isRegularFile(path) &&
               Files.size(path) >= minSizeBytes;
    }
}

// Context: coordinates filtering and directory traversal
class FindService {

    // Starts the search from the root directory
    public List<Path> search(
            Path root,
            List<SearchFilter> filters) throws IOException {

        // Validate the root directory
        if (root == null || !Files.isDirectory(root)) {
            throw new IllegalArgumentException(
                    "Invalid directory: " + root);
        }

        // Stores all matching paths
        List<Path> results = new ArrayList<>();

        // Handle null filters by using an empty list
        List<SearchFilter> activeFilters =
                filters == null
                        ? Collections.emptyList()
                        : filters;

        // Start recursive DFS traversal
        searchRecursively(root, activeFilters, results);

        // Return all matching paths
        return results;
    }

    // Recursively visits each file and directory
    private void searchRecursively(
            Path path,
            List<SearchFilter> filters,
            List<Path> results) throws IOException {

        // Initially assume the path matches all filters
        boolean matches = true;

        // Apply all strategies using AND logic
        for (SearchFilter filter : filters) {

            // Delegate matching logic to the selected strategy
            if (!filter.matches(path)) {
                matches = false;
                break;
            }
        }

        // Add the path only if every filter matches
        if (matches) {
            results.add(path);
        }

        // Continue traversing even if the current path doesn't match
        if (Files.isDirectory(path)) {

            // Open the directory and automatically close the stream
            try (DirectoryStream<Path> children =
                         Files.newDirectoryStream(path)) {

                // Recursively search every child (DFS)
                for (Path child : children) {
                    searchRecursively(child, filters, results);
                }
            }
        }
    }
}

// Client: configures the search and executes it
public class Main {

    public static void main(String[] args) {

        // Create the search service
        FindService service = new FindService();

        // Configure the required search strategies
        List<SearchFilter> filters = Arrays.asList(
                new TypeFilter(EntryType.FILE),
                new ExtensionFilter(".java"),
                new SizeFilter(1024)
        );

        // Specify the root directory; "." means current directory
        Path root = Paths.get(".");

        try {
            // Execute the search
            List<Path> results = service.search(root, filters);

            // Print all matching paths
            for (Path path : results) {
                System.out.println(path);
            }

            System.out.println("Total matches: " + results.size());

        } catch (IOException e) {
            // Handle file-system errors
            System.err.println("Search failed: " + e.getMessage());
        }
    }
}
