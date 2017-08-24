package springbook.learningTest.templte;

public interface LineCallback<T> {
	T doSomethingWithLine(String line, T value);
}
