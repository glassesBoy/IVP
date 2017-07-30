Ext.define('ivp.desktop.common.App', {
    extend : 'ivp.desktop.core.App',

    requires : ['ivp.desktop.core.ShortcutModel', 'ivp.desktop.common.Settings'],

    alias : 'widget.desktopmain',
    itemId : 'desktopmainId',

    appsStore : null,
    init : function() {
        // custom logic before getXYZ methods get called... #098279

        this.callParent();

        // now ready...
    },

    getAppsStore : function() {
        // return this.appsStore;
        // console.log('============== this.appsStore ',this.appsStore);
        // return Ext.create('Ext.data.Store', {
        // model : 'ivp.desktop.core.ShortcutModel',
        // data : this.appsStore
        // });

        return Ext.create('Ext.data.Store', {
            model : 'ivp.desktop.core.ShortcutModel',
            data : [{
                name : "<span style='font-size:12px;font-weight:bold;'>车辆管理</span>",
                appIconCls : 'icon-vehicle48',
                module : 'vehicleview',
                classpath : 'ivp.view.vehicle.VehicleView'
            }, {
                name : "<span style='font-size:12px;font-weight:bold;'>评分标准</span>",
                appIconCls : 'icon-vrating48',
                module : 'ratingview',
                classpath : 'ivp.view.rating.RatingView'
            }, {
                name : "<span style='font-size:12px;font-weight:bold;'>GPS信息管理</span>",
                appIconCls : 'icon-gpsdata48',
                module : 'gpsappview',
                classpath : 'ivp.view.gpsdata.AppView'
            }, {
                name : "<span style='font-size:12px;font-weight:bold;'>比赛信息展示</span>",
                appIconCls : 'icon-track48',
                module : 'trackmainview',
                classpath : 'ivp.view.track.TrackMainView'
            // // }, {
            // // name : 'System Status',
            // // iconCls : 'cpu-shortcut',
            // // module : 'systemstatus'
            }]
        });
    },

    getModules : function() {
        var me = this;
        var apps = me.getAppsStore();
        var items = apps.data.items;
        var appArr = [];
        for (var i = 0, len = items.length; i < len; i++) {
            // 如果写了 类路径
            if (items[i].data.classpath) {
                appArr.push(Ext.create(items[i].data.classpath));
            }
        }
        return appArr;
    },

    getDesktopConfig : function() {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            // cls: 'ux-desktop-black',
            contextMenuItems : [{
                text : '设置',
                handler : me.onSettings,
                scope : me
            }],

            shortcuts : me.getAppsStore(),

            wallpaper : 'app/desktop/wallpapers/bg.jpg',
            wallpaperStretch : true
        });
    },

    // config for the start menu
    getStartConfig : function() {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            // title : 'Don Griffin',
            title : '[开始菜单]',
            iconCls : 'user',
            height : 300,
            toolConfig : {
                width : 100,
                items : [{
                    text : '设置',
                    iconCls : 'settings',
                    handler : me.onSettings,
                    scope : me
                }, '-', {
                    text : 'XX设置',
                    iconCls : 'settings',
                    // iconCls : 'modifyPassword',
                    // handler : me.modifyPassword,
                    scope : me
                }, '-', {
                    text : 'XXX设置',
                    iconCls : 'settings',
                    // iconCls : 'logout',
                    // handler : me.onLogout,
                    scope : me

                }]
            }
        });
    },

    getTaskbarConfig : function() {
        var ret = this.callParent();

        return Ext.apply(ret, {
            // quickStart : [{
            // name : '示例程序1',
            // iconCls : 'accordion',
            // module : 'acc-win'
            // }, {
            // name : '示例程序2',
            // iconCls : 'icon-grid',
            // module : 'grid-win'
            // }],
            trayItems : [{
                xtype : 'trayclock',
                flex : 1
            }]
        });
    },

    onSettings : function() {
        // var dlg = new ivp.desktop.common.Settings({
        // desktop : this.desktop
        // });
        // dlg.show();

        Ext.MessageBox.alert('', '设置');
    },
    /**
     * 系统配置
     */
    sysSetting : function() {
        // 打开系统配置窗口之前，先输入密码
        var win = Ext.create('ivp.view.sys.SystemSetting', {});
        if (win) {
            win.show();
        }
        // },
        // jumpToOtherOrganization : function() {
        // Ext.MessageBox.alert('', '跳转到其他Organization');
    }

});
