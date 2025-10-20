-- 创建数据库表结构初始化脚本

-- 创建游戏分类表
CREATE TABLE IF NOT EXISTS game_categories (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '分类名称',
    status TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_status (status)
) COMMENT '游戏分类表';

-- 创建游戏攻略表
CREATE TABLE IF NOT EXISTS game_guides (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '攻略ID',
    title VARCHAR(200) NOT NULL COMMENT '攻略标题',
    content TEXT COMMENT '攻略内容',
    category_id INT NOT NULL COMMENT '分类ID',
    cover_image VARCHAR(500) COMMENT '封面图片URL',
    video_url VARCHAR(500) COMMENT '视频URL',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    status CHAR(1) DEFAULT 'A' COMMENT '状态：A-正常，D-删除',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (category_id) REFERENCES game_categories(id) ON DELETE CASCADE,
    INDEX idx_category_id (category_id),
    INDEX idx_status (status),
    INDEX idx_view_count (view_count),
    INDEX idx_created_at (created_at)
) COMMENT '游戏攻略表';

-- 创建游戏资讯表
CREATE TABLE IF NOT EXISTS game_news (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '资讯ID',
    title VARCHAR(200) NOT NULL COMMENT '资讯标题',
    summary VARCHAR(500) COMMENT '资讯摘要',
    content TEXT COMMENT '资讯内容',
    cover_image VARCHAR(500) COMMENT '封面图片URL',
    status CHAR(1) DEFAULT 'A' COMMENT '状态：A-正常，D-删除',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) COMMENT '游戏资讯表';

-- 创建Banner表
CREATE TABLE IF NOT EXISTS banners (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Banner ID',
    title VARCHAR(100) NOT NULL COMMENT 'Banner标题',
    image_url VARCHAR(500) NOT NULL COMMENT '图片URL',
    link_url VARCHAR(500) COMMENT '跳转链接',
    sort_order INT DEFAULT 0 COMMENT '排序',
    is_active TINYINT DEFAULT 1 COMMENT '是否激活：1-是，0-否',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_is_active (is_active),
    INDEX idx_sort_order (sort_order)
) COMMENT 'Banner表';

-- 插入默认游戏分类数据
INSERT IGNORE INTO game_categories (id, name, status) VALUES 
(1, '动作游戏', 1),
(2, '角色扮演', 1),
(3, '策略游戏', 1),
(4, '休闲益智', 1),
(5, '射击游戏', 1);

-- 插入默认Banner数据
INSERT IGNORE INTO banners (id, title, image_url, link_url, sort_order, is_active) VALUES 
(1, '热门游戏推荐', 'https://example.com/banner1.jpg', 'https://example.com/game1', 1, 1),
(2, '最新攻略', 'https://example.com/banner2.jpg', 'https://example.com/guide1', 2, 1),
(3, '游戏资讯', 'https://example.com/banner3.jpg', 'https://example.com/news1', 3, 1);