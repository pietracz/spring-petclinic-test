package org.springframework.samples.petclinic.system;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/* ExtendWith is a annotation that extends the test with Mockito */
@ExtendWith(MockitoExtension.class)
public class KubernetesControllerTest {

    @Mock
    /* Makes the variable of type CoreV1Api only accessable inside a class  */
    private CoreV1Api mockApi;

    private KubernetesController controller;
    private MockMvc mockMvc;

    @BeforeEach

    public void setUp() {
        controller = new TestKubernetesController(mockApi);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    /*  */
    public void testGetPods() throws Exception {
        V1Pod pod1 = new V1Pod().metadata(new V1ObjectMeta().name("pod1"));
        V1Pod pod2 = new V1Pod().metadata(new V1ObjectMeta().name("pod2"));

        V1PodList podList = new V1PodList();
        podList.setItems(Arrays.asList(pod1, pod2));

        when(mockApi.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null)).thenReturn(podList);

        mockMvc.perform(get("/pods"))
            .andExpect(status().isOk())
            .andExpect(content().string("[\"pod1\", \"pod2\"]"));
    }

     private static class TestKubernetesController extends KubernetesController {
        private final CoreV1Api mockApi;
        
        public TestKubernetesController(CoreV1Api mockApi) {
            super(mockApi);
            this.mockApi = mockApi;
        }
        
        @Override
        protected CoreV1Api getApi() {
            return mockApi;
        }
    }

    }