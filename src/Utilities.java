public class Utilities {
    public static Object[] copyArray(Object[] objects, int newLength) {
        Object[] arrayCopy = objects;
        objects = new Song[newLength];
        for (int i = 0; i < objects.length; i++) {
            if (arrayCopy.length < i) {
                return objects;
            }
            objects[i] = arrayCopy[i];
        }
        return objects;
    }

    public static int findEmptyPosition(Object[] objects) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
