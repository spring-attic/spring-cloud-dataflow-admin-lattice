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

import org.cloudfoundry.receptor.client.ReceptorClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.dataflow.module.deployer.ModuleDeployer;
import org.springframework.cloud.lattice.LatticeProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Configuration used when running on Lattice.
 *
 * @author Mark Fisher
 * @author Thomas Risberg
 * @author Ilayaperumal Gopinathan
 */
@Configuration
public class LatticeConfiguration {

	protected static class LatticeConfig {

		@Autowired
		private LatticeProperties latticeProperties;

		@Bean
		public ModuleDeployer processModuleDeployer() {
			return new LrpModuleDeployer(receptorClient(), latticeProperties.getReceptor().getHost());
		}

		@Bean
		public ModuleDeployer taskModuleDeployer() {
			return new TaskModuleDeployer();
		}

		@Bean
		public ReceptorClient receptorClient() {
			return new ReceptorClient(latticeProperties.getReceptor().getHost());
		}
	}

	@AutoConfigureBefore(RedisAutoConfiguration.class)
	@Profile("lattice")
	protected static class RedisConfig {

		@Bean
		public Cloud cloud(CloudFactory cloudFactory) {
			return cloudFactory.getCloud();
		}

		@Bean
		public CloudFactory cloudFactory() {
			return new CloudFactory();
		}

		@Bean
		RedisConnectionFactory redisConnectionFactory(Cloud cloud) {
			return cloud.getSingletonServiceConnector(RedisConnectionFactory.class, null);
		}
	}
}
