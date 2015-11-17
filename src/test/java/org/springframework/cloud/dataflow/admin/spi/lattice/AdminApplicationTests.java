/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.dataflow.admin.spi.lattice;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.dataflow.admin.AdminApplication;
import org.springframework.cloud.lattice.LatticeProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Tests for the {@link AdminApplication} on Lattice.
 *
 * @author Janne Valkealahti
 */
public class AdminApplicationTests {

	@Test
	public void testLatticeProperties() {
		SpringApplication app = new SpringApplication(AdminApplication.class);
		ConfigurableApplicationContext context = app.run(new String[] {
				"--server.port=0" , "--spring.cloud.lattice.receptor.host=receptor.52.7.176.225.xip.io"});
		assertThat(context.containsBean("latticeProperties"), is(true));
		assertThat(context.getBean("latticeProperties", LatticeProperties.class).getReceptor().getHost(), is("receptor.52.7.176.225.xip.io"));
		context.close();
	}

}
