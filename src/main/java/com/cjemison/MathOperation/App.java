package com.cjemison.MathOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class App {

  // from two arrays with randomly generates numbers,
  // tell me which to array indices has the combine value for the buckets.
  public static void main(String[] args) {
    final App app = new App();

    final Operation operation = (a, b) -> a + b;

    // two arrays with randomly number between 1 - 10
    final int[] array1 = app.createRandomIntegerArray(1000);
    final int[] array2 = app.createRandomIntegerArray(1000);

    final int value = operation.apply(array1[Math.abs(new Random().nextInt() % array1.length)],
          array2[Math.abs(new Random().nextInt() % array2.length)]);


    // load the permutations to list
    final List<Node> list = new ArrayList<>();
    for (int i = 0; i < array1.length; i++) {
      for (int j = 0; j < array1.length; j++) {
        list.add(new Node(i, j, operation.apply(array1[i], array2[j])));
      }
    }

    // o(n) search for the value
    list.stream().filter(node -> node.getValue() == value)
          .forEach(node -> {
            System.out.println("-----");

            System.out.println(String.format("Array 1: %d  value: %d", node.getIndexA(), array1[node
                  .getIndexA()]));

            System.out.println(String.format("Array 2: %d  value: %d", node.getIndexB(), array2[node
                  .getIndexB()]));

          });
  }

  private static class Node {
    private final int indexA;
    private final int indexB;
    private final int value;

    public Node(final int indexA,
                final int indexB,
                final int value) {
      this.indexA = indexA;
      this.indexB = indexB;
      this.value = value;
    }

    public int getIndexA() {
      return indexA;
    }

    public int getIndexB() {
      return indexB;
    }

    public int getValue() {
      return value;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof Node)) return false;
      final Node node = (Node) o;
      return getIndexA() == node.getIndexA() &&
            getIndexB() == node.getIndexB();
    }

    @Override
    public int hashCode() {

      return Objects.hash(getIndexA(), getIndexB());
    }
  }

  private int[] createRandomIntegerArray(final int size) {
    final int[] array = new int[size];
    IntStream.range(0, size).boxed().forEach(i -> array[i] = randomInt(1000));
    return array;
  }

  private int randomInt(int seed) {
    return Math.abs(new Random().nextInt() % seed) + 1;
  }

  @FunctionalInterface
  public interface Operation {
    int apply(final int x, int y);
  }
}
