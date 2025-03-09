0. Installing [Java SDK](https://www.oracle.com/java/technologies/downloads/#jdk23-windows), [Choco](https://chocolatey.org/), [kubectl](https://kubernetes.io/de/docs/tasks/tools/install-kubectl/) and [k3d](https://k3d.io/stable/)
1. Using [Kubernetes Java Client](https://github.com/kubernetes-client/java) as guide/documentation
2. Since mavn and gradle project already is set up, only need to add dependencies to pom.xml (added to the bottom of dependencies)

```
<dependency>
    <groupId>io.kubernetes</groupId>
    <artifactId>client-java</artifactId>
    <version>15.0.1</version>
</dependency>
```

3. Changing the @Controller annotation to @RestController within the WelcomeController
4. Adding file to system directory 'KubernetesController.java'
5. Used the example file of the documentation - caused error on building
6. Took the error-log ran it through Claude Sonnet 3.7 build in my IDE - rewrote the file
7. ` kubectl apply -f k8s/db.yml`
   kubectl apply -f k8s/petclinic.yml `
8. ./mvnw spring-boot:run
9. Took the error-log again - builded to the ASCII Petclinic Logo - ran it through my IDE again
10. Added snakeyaml to dependencies in pom.xml
11. ./mvnw spring-boot:run
12. curl localhost:8080/pods
13. Output: `["demo-db-949d9c68f-p7qzx","petclinic-579f9bf9db-z5qtd","coredns-ccb96694c-4gwp7","helm-install-traefik-crd-n8stx","helm-install-traefik-tsz8h","local-path-provisioner-5cf85fd84d-fd8ws","metrics-server-5985cbc9d7-4bhbj","svclb-traefik-8596d349-crhm4","traefik-5d45fc8cc9-h6jjm"]`
14. Added file to test directory 'KubernetesControllerTest.java'
15. Added

Sidenote:

- used `./mvnw spring-javaformat:apply` to format the code
