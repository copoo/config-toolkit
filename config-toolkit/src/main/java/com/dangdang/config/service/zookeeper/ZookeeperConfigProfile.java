/**
 * Copyright 1999-2014 dangdang.com.
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
package com.dangdang.config.service.zookeeper;

import com.dangdang.config.service.ConfigProfile;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;

/**
 * 基本配置
 * 
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class ZookeeperConfigProfile extends ConfigProfile {

	private static final ExponentialBackoffRetry DEFAULT_RETRY_POLICY = new ExponentialBackoffRetry(1000, 3);

	/**
	 * zookeeper地址
	 */
	private final String connectStr;

	/**
	 * 项目配置根目录
	 */
	private final String rootNode;

	/**
	 * 重试策略
	 */
	private final RetryPolicy retryPolicy;

	public ZookeeperConfigProfile(final String connectStr, final String rootNode, final boolean openLocalCache) {
		this(connectStr, rootNode, null, openLocalCache, DEFAULT_RETRY_POLICY);
	}

	public ZookeeperConfigProfile(final String connectStr, final String rootNode, final String version) {
		this(connectStr, rootNode, version, false, DEFAULT_RETRY_POLICY);
	}

	public ZookeeperConfigProfile(final String connectStr, final String rootNode, final String version, final boolean openLocalCache,
			final RetryPolicy retryPolicy) {
		super(version);
		this.connectStr = Preconditions.checkNotNull(connectStr);
		this.rootNode = Preconditions.checkNotNull(rootNode);
		this.retryPolicy = Preconditions.checkNotNull(retryPolicy);
	}

	public String getConnectStr() {
		return connectStr;
	}

	public String getRootNode() {
		return rootNode;
	}

	public RetryPolicy getRetryPolicy() {
		return retryPolicy;
	}

	public String getVersionedRootNode() {
		if (Strings.isNullOrEmpty(version)) {
			return rootNode;
		}
		return ZKPaths.makePath(rootNode, version);
	}

	@Override
	public String toString() {
		return "ZookeeperConfigProfile{" +
				"connectStr='" + connectStr + '\'' +
				", rootNode='" + rootNode + '\'' +
				", retryPolicy=" + retryPolicy +
				'}';
	}
}
