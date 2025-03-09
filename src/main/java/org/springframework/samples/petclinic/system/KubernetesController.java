package org.springframework.samples.petclinic.system;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class KubernetesController {

	private CoreV1Api api;

	private boolean kubernetesAvailable = false;

	public KubernetesController() throws IOException {
		try {
			ApiClient client = Config.defaultClient();
			Configuration.setDefaultApiClient(client);
			this.api = new CoreV1Api();
			this.kubernetesAvailable = true;
		}
		catch (Exception e) {
			System.out.println("Kein Kubernetes verfügbar: " + e.getMessage());
			this.kubernetesAvailable = false;
		}

	}

	protected KubernetesController(CoreV1Api api) {
		this.api = api;
		this.kubernetesAvailable = true;
	}

	protected CoreV1Api getApi() {
		return this.api;
	}

	@GetMapping("/pods")
	public List<String> getPods() throws ApiException {
		if (!kubernetesAvailable) {
			List<String> mockPods = new ArrayList<>();
			mockPods.add("Kein Kubernetes-Cluster verfügbar");
			return mockPods;
		}

		try {
			V1PodList podList = getApi().listPodForAllNamespaces(null, null, null, null, null, null, null, null, null,
					null);
			return podList.getItems().stream().map(pod -> pod.getMetadata().getName()).collect(Collectors.toList());
		}
		catch (ApiException e) {
			List<String> errorList = new ArrayList<>();
			errorList.add("Fehler beim Abfragen von Kubernetes: " + e.getMessage());
			return errorList;
		}
	}

}