package com.oasys.uppcl_user__master_management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.oasys.uppcl_user__master_management.cache.dto.ServiceProviderCacheDTO;
import com.oasys.uppcl_user__master_management.cache.dto.ServiceSlotCacheDTO;
import com.oasys.uppcl_user__master_management.cache.dto.SubCategoryCacheDTO;
import com.oasys.uppcl_user__master_management.cache.serlizable.ServiceProviderStreamSerializer;
import com.oasys.uppcl_user__master_management.cache.serlizable.ServiceStreamSerializer;
import com.oasys.uppcl_user__master_management.cache.serlizable.SlotStreamSerializer;
import com.oasys.uppcl_user__master_management.cache.serlizable.SubCategoryStreamSerializer;
import com.oasys.uppcl_user__master_management.dto.ServiceMasterCacheDTO;
@Configuration
public class CacheConfig {

	public Config createConfig() {
		Config config = new Config();
		config.getSerializationConfig().addSerializerConfig(serializerConfig());
		config.getSerializationConfig().addSerializerConfig(serializerConfigService());
		config.getSerializationConfig().addSerializerConfig(serializerConfigServiceProvider());
		config.getSerializationConfig().addSerializerConfig(serializerConfigSubcategory());
		return config;
	}

	private SerializerConfig serializerConfig() {
		return new SerializerConfig().setImplementation(new SlotStreamSerializer())
				.setTypeClass(ServiceSlotCacheDTO.class);
	}

	private SerializerConfig serializerConfigService() {
		return new SerializerConfig().setImplementation(new ServiceStreamSerializer())
				.setTypeClass(ServiceMasterCacheDTO.class);
	}

	private SerializerConfig serializerConfigSubcategory() {
		return new SerializerConfig().setImplementation(new SubCategoryStreamSerializer())
				.setTypeClass(SubCategoryCacheDTO.class);
	}

	private SerializerConfig serializerConfigServiceProvider() {
		return new SerializerConfig().setImplementation(new ServiceProviderStreamSerializer())
				.setTypeClass(ServiceProviderCacheDTO.class);
	}
	
	@Bean
	public HazelcastInstance hazelcastInstance() {
		return Hazelcast.newHazelcastInstance(createConfig());
	}
}
