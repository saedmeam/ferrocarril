/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferrocarril;
import java.util.*;

class TrainRoute {
    static class Edge {
        char destination;
        int weight;

        Edge(char destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    static Map<Character, List<Edge>> graph = new HashMap<>();

    public static void main(String[] args) {
        // Construir el grafo según los datos de entrada
        construirGrafo("AB5");
        construirGrafo("BC4");
        construirGrafo("CD8");
        construirGrafo("DC8");
        construirGrafo("DE6");
        construirGrafo("AD5");
        construirGrafo("CE2");
        construirGrafo("EB3");
        construirGrafo("AE7");

        // Realizar las consultas
        System.out.println("Output #1: " + hallarDistancia("ABC"));
        System.out.println("Output #2: " + hallarDistancia("AD"));
        System.out.println("Output #3: " + hallarDistancia("ADC"));
        System.out.println("Output #4: " + hallarDistancia("AEBCD"));
        System.out.println("Output #5: " + hallarDistancia("AED"));
        System.out.println("Output #6: " + countTripsWithMaxStops('C', 'C', 3));
        System.out.println("Output #7: " + countTripsWithExactStops('A', 'C', 4));
        System.out.println("Output #8: " + findShortestRoute('A', 'C'));
        System.out.println("Output #9: " + findShortestRoute('B', 'B'));
        System.out.println("Output #10: " + countRoutesWithDistanceLessThan('C', 'C', 30));
    }

    // Método para construir el grafo
    static void construirGrafo(String route) {
        char source = route.charAt(0);
        char destination = route.charAt(1);
        int weight = Character.getNumericValue(route.charAt(2));

        if (!graph.containsKey(source)) {
            graph.put(source, new ArrayList<>());
        }
        graph.get(source).add(new Edge(destination, weight));
    }

    // Método para encontrar la distancia de una ruta
    static String hallarDistancia(String route) {
        int distance = 0;
        for (int i = 0; i < route.length() - 1; i++) {
            char source = route.charAt(i);
            char destination = route.charAt(i + 1);
            List<Edge> edges = graph.get(source);
            boolean found = false;
            if (edges != null) {
                for (Edge edge : edges) {
                    if (edge.destination == destination) {
                        distance += edge.weight;
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                return "NO SUCH ROUTE";
            }
        }
        return String.valueOf(distance);
    }

    // Método para contar los viajes con un número máximo de paradas
    static int countTripsWithMaxStops(char start, char end, int maxStops) {
        int count = 0;
        for (int i = 1; i <= maxStops; i++) {
            count += countTripsWithExactStops(start, end, i);
        }
        return count;
    }

    // Método para contar los viajes con un número exacto de paradas
    static int countTripsWithExactStops(char start, char end, int stops) {
        if (stops == 0) {
            return start == end ? 1 : 0;
        }
        int count = 0;
        List<Edge> edges = graph.get(start);
        if (edges != null) {
            for (Edge edge : edges) {
                count += countTripsWithExactStops(edge.destination, end, stops - 1);
            }
        }
        return count;
    }

    // Método para encontrar la ruta más corta
    static String findShortestRoute(char start, char end) {
        Queue<Map.Entry<Character, Integer>> queue = new LinkedList<>();
        queue.add(new AbstractMap.SimpleEntry<>(start, 0));

        while (!queue.isEmpty()) {
            Map.Entry<Character, Integer> entry = queue.poll();
            char current = entry.getKey();
            int distance = entry.getValue();

            if (current == end&& distance>0) {
                return String.valueOf(distance);
            }

            List<Edge> edges = graph.get(current);
            if (edges != null) {
                for (Edge edge : edges) {
                    queue.add(new AbstractMap.SimpleEntry<>(edge.destination, distance + edge.weight));
                }
            }
        }

        return "NO SUCH ROUTE";
    }

    // Método para contar las rutas con una distancia menor que un valor dado
    static int countRoutesWithDistanceLessThan(char start, char end, int maxDistance) {
        return countRoutesWithDistanceLessThanHelper(start, end, maxDistance, 0);
    }

    static int countRoutesWithDistanceLessThanHelper(char current, char end, int maxDistance, int currentDistance) {
        System.out.println("distancia actual "+currentDistance+" current "+current+" end "+ end);
                int count = 0;

        if (currentDistance >= maxDistance) {
            return 0;
        }

        if (current == end && currentDistance > 0
                ) {
            count++ ;
        }

        List<Edge> edges = graph.get(current);
        if (edges != null) {
            for (Edge edge : edges) {
                count += countRoutesWithDistanceLessThanHelper(edge.destination, end, maxDistance, currentDistance + edge.weight);
            }
        }
        return count;
    }
}
