# آزمایشگاه نرم افزار - آزمایش 8

آز آن جا که کلمات انگلیسی استفاده شده در متن زیاد بود، فارسی نوشتن این فایل بسیار دشوار شد؛ لذا تصمیم گرفتیم این فایل را به انگلیسی بنویسیم. باشد که مقبول افتد!

# Technical Report: Graph Traversal Library Migration Using Design Patterns

## Project Overview

This project demonstrates the implementation of Adapter and Strategy design patterns to migrate a Java graph traversal application from the JUNG library to JGraphT while maintaining existing functionality and minimizing code changes.

## Table of Contents

- [Section 1: Adapter Pattern Implementation](#section-1-adapter-pattern-implementation)
- [Section 2: Library Change Details](#section-2-library-change-details)
- [Section 3: Strategy Pattern Analysis](#section-3-strategy-pattern-analysis)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Conclusion](#conclusion)

## Section 1: Adapter Pattern Implementation

### Adapter Type Selection

**Decision: Object Scope Adapter**

We chose the Object Scope Adapter pattern over the Class Scope Adapter for the following reasons:

#### 1. Java Language Constraints

- Java supports only single inheritance
- Class Scope Adapter would require multiple inheritance (impossible in Java)
- Object Scope Adapter uses composition, which is more flexible

#### 2. Design Flexibility

```java
public class JungGraphAdapter implements GraphAdapter<Integer, String> {
private final SparseMultigraph<Integer, String> jungGraph; // Composition
// ...
}
```

#### 3. Encapsulation Benefits

- Better encapsulation of the wrapped library
- Clear separation between the adapter interface and the library implementation
- Easier to modify internal implementation without affecting clients

#### 4. Testing and Maintenance

- Easier unit testing with dependency injection
- Better maintainability and extensibility
- Supports runtime configuration

### Implementation Method

#### Core Interface Design

```java
public interface GraphAdapter<V, E> {
boolean addVertex(V vertex);
boolean addEdge(E edge, V source, V target);
Collection<V> getNeighbors(V vertex);
Set<V> getVertices();
boolean containsVertex(V vertex);
int getVertexCount();
}
```

#### JUNG Adapter Implementation

```java
public class JungGraphAdapter implements GraphAdapter<Integer, String> {
private final SparseMultigraph<Integer, String> jungGraph;

@Override
public Set<Integer> getVertices() {
// Type conversion: Collection<Integer> to Set<Integer>
return new HashSet<>(jungGraph.getVertices());
}
// ... other methods
}
```

#### Key Implementation Challenges

1. **Type Mismatch Resolution**: JUNG returns `Collection<Integer>` while our interface requires `Set<Integer>`
2. **Method Mapping**: Direct mapping of JUNG methods to our interface
3. **Edge Handling**: Consistent edge management across different libraries

## Section 2: Library Change Details

### Migration Process

#### Step 1: Interface Preservation

The `GraphAdapter` interface remained unchanged, ensuring:

- Zero modifications to client code (traversers)
- Consistent API across different library implementations
- Seamless library swapping capability

#### Step 2: New Adapter Implementation

```java
public class JGraphTAdapter implements GraphAdapter<Integer, String> {
private final DefaultUndirectedGraph<Integer, DefaultEdge> jgraphtGraph;
private final Map<String, DefaultEdge> edgeMap;

@Override
public Collection<Integer> getNeighbors(Integer vertex) {
return Graphs.neighborListOf(jgraphtGraph, vertex);
}
// ... other methods
}
```

#### Step 3: Dependency Management

**Before (JUNG only):**

```xml
<dependency>
<groupId>net.sf.jung</groupId>
<artifactId>jung-graph-impl</artifactId>
<version>2.1.1</version>
</dependency>
```

**After (JGraphT):**

```xml
<dependency>
<groupId>org.jgrapht</groupId>
<artifactId>jgrapht-core</artifactId>
<version>1.5.2</version>
</dependency>
```

### Files Changed vs. Unchanged

#### Files Requiring Changes

| File        | Change Required            | Reason                           |
| ----------- | -------------------------- | -------------------------------- |
| `Main.java` | Adapter instantiation only | Different constructor parameters |
| `pom.xml`   | Dependency update          | Library change                   |

#### Files Unchanged

| File                     | Status        | Benefit                 |
| ------------------------ | ------------- | ----------------------- |
| `BfsGraphTraverser.java` | ✅ No changes | Adapter pattern success |
| `DfsGraphTraverser.java` | ✅ No changes | Adapter pattern success |
| `GraphAdapter.java`      | ✅ No changes | Interface stability     |

### Migration Benefits

1. **Minimal Client Impact**: Only instantiation code changed
2. **Risk Reduction**: Core logic remains untested
3. **Rollback Capability**: Easy to revert if needed
4. **Future Flexibility**: Can support multiple libraries simultaneously

## Section 3: Strategy Pattern Analysis

### Why the Strategy Pattern is Appropriate

#### 1. Algorithm Family

Graph traversal has multiple algorithms:

- **Breadth-First Search (BFS)**: Level-by-level exploration
- **Depth-First Search (DFS)**: Deep exploration before backtracking

#### 2. Runtime Algorithm Selection

```java
// Client can choose the algorithm at runtime
Traverser bfsTraverser = new BfsGraphTraverser(adapter);
Traverser dfsTraverser = new DfsGraphTraverser(adapter);
```

#### 3. Open/Closed Principle

- **Open for extension**: Easy to add new traversal algorithms
- **Closed for modification**: Existing algorithms remain unchanged

#### 4. Single Responsibility

Each strategy class has one responsibility:

- `BfsGraphTraverser`: Implements BFS logic only
- `DfsGraphTraverser`: Implements DFS logic only

### Strategy Pattern Operation

#### Component Structure

Context (Main Application)

↓ uses

Strategy Interface (Traverser)

↓ implemented by

Concrete Strategies (BfsGraphTraverser, DfsGraphTraverser)

#### 1. Context (Main Application)

```java
public class Main {
public static void main(String[] args) {
GraphAdapter<Integer, String> adapter = new JungGraphAdapter();
// Context chooses strategy
Traverser traverser = new BfsGraphTraverser(adapter);
List<Integer> path = traverser.traverse(startVertex);
}
}
```

#### 2. Strategy Interface

```java
public interface Traverser {
List<Integer> traverse(Integer startVertex);
}
```

#### 3. Concrete Strategies

```java
public class BfsGraphTraverser implements Traverser {
// BFS-specific implementation
}

public class DfsGraphTraverser implements Traverser {
// DFS-specific implementation
}
```

#### Analysis Questions Response

##### Why is use of this pattern appropriate here?

The Strategy pattern is highly appropriate for graph traversal implementation because:

- Algorithm Variability: Different traversal algorithms (BFS, DFS) need to be supported with the same interface
- Runtime Selection: Applications may need to choose traversal strategy based on runtime conditions (graph size, performance requirements, etc.)
- Open/Closed Principle: New traversal algorithms can be added without modifying existing code
- Code Reusability: The same graph structure can be traversed using different strategies without duplication

##### Pattern Operation Method (3 lines):

- Context: The main application or client code that needs to perform graph traversal operations
- Strategy Interface: The Traverser interface that defines the contract (traverse(Integer startVertex)) that all concrete strategies must implement
- Concrete Strategies: Individual algorithm implementations (BfsGraphTraverser, DfsGraphTraverser) that provide specific traversal logic while maintaining the same interface
  The pattern allows the context to use different traversal algorithms interchangeably without knowing the implementation details of each strategy.

#### Strategy Pattern Benefits

1. **Algorithm Encapsulation**: Each algorithm is self-contained
2. **Easy Testing**: Each strategy can be tested independently
3. **Code Reusability**: Strategies can be reused across different contexts
4. **Maintainability**: Changes to one algorithm don't affect others

## Project Structure

```
GraphTraverser/
├── src/
│ ├── main/java/org/example/
│ │ ├── adapter/
│ │ │ ├── GraphAdapter.java
│ │ │ ├── JungGraphAdapter.java
│ │ │ └── JGraphTAdapter.java
│ │ ├── graphTravelers/
│ │ │ ├── Traverser.java
│ │ │ ├── BfsGraphTraverser.java
│ │ │ └── DfsGraphTraverser.java
│ │ ├── Main.java
│ │ └── MainJGraphT.java
├── Adapter Implementation - 01/
└── Library Change - 02/
```

## Dependencies

### Core Dependencies

- **JUNG**: `jung-graph-impl:2.1.1`
- **JGraphT**: `jgrapht-core:1.5.2`

## Conclusion

### Design Pattern Success Metrics

#### Adapter Pattern

- ✅ **Library Independence**: Achieved complete decoupling
- ✅ **Code Reusability**: Core traversal logic unchanged
- ✅ **Migration Ease**: Minimal changes required

#### Strategy Pattern

- ✅ **Algorithm Flexibility**: Easy to add new traversal methods
- ✅ **Runtime Selection**: Dynamic algorithm choice capability
- ✅ **Maintainability**: Clean separation of concerns

### Project Impact

This implementation demonstrates how proper design patterns can:

1. **Reduce Technical Debt**: Clean architecture prevents coupling
2. **Enable Scalability**: Easy to add new libraries and algorithms
3. **Improve Maintainability**: Clear separation of responsibilities
4. **Facilitate Testing**: Isolated components for better test coverage
