/**
 * 菜单
 */
Ext.define('ivp.view.MenuView', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.menuview',
    xtype : 'menuview',
    itemId : 'menuviewId',

    layout : 'fit',
    initComponent : function() {
        var me = this;
        // var menuStore = Ext.create('Ext.data.TreeStore', {
        // model : 'wzglxt.model.modules.CommonModel',
        // autoLoad : true,
        // storeId : 'modulesview',
        // nodeParam : 'id',
        // proxy : {
        // type : 'ajax',
        // url : 's/modules/s',
        // reader : {
        // type : "json",
        // root : "data",
        // successProperty : 'success',
        // totalProperty : 'total'
        // }
        // }
        // });

        Ext.applyIf(me, {
            items : [{
                xtype : 'treepanel',
                rootVisible : false,
                // stripeRows : true,
                // columnLines : true,
                // checked : true,
                displayField : 'menuName',

                store : {
                    store : 'tree',
                    fields : ['menuID', 'menuName', 'mXType', 'mClassPath'],
                    root : {
                        "menuName" : "systemroot",
                        "margins" : "0 0 5 0",
                        "expanded" : true,
                        "children" : [{
                            'menuID' : 1,
                            'menuName' : '车辆管理',
                            leaf : false,
                            "expanded" : true,
                            "children" : [{
                                'menuID' : 11,
                                leaf : true,
                                'menuName' : '车队/车辆信息',
                                'mXType' : 'vehicleview',
                                'mClassPath' : 'ivp.view.VehicleView'
                            }]
                        }, {
                            'menuID' : 2,
                            'menuName' : '打分标准',
                            'mXType' : '',
                            leaf : true,
                            'mClassPath' : ''
                        }, {
                            'menuID' : 3,
                            'menuName' : '车辆GPS信息导入',
                            'mXType' : '',
                            leaf : true,
                            'mClassPath' : ''
                        }, {
                            'menuID' : 4,
                            leaf : true,
                            'menuName' : '系统设置',
                            'mXType' : '',
                            'mClassPath' : ''
                        }]
                    }
                },

                listeners : {
                    select : {
                        fn : me.onTreepanelSelect,
                        scope : me
                    }
                }
            }]
        });
        me.callParent(arguments);
    },

    onTreepanelSelect : function(rowmodel, record, index, eOpts) {
        // console.log('============== rowmodel ', rowmodel);
        // console.log('============== record ', record);
        if (!record.data.mXType) {
            return false;
        }

        var treepanel = rowmodel.view.up('treepanel');
        // console.log('===========treepanel ', treepanel);

        var tPanel = treepanel.up('menuview').up().down('tabpanel');
        // console.log('======== tPanel ', tPanel);

        var viewType = '[xtype=' + record.data.mXType + ']';
        // console.log('============ viewType ', viewType);
        var targetTab = tPanel.child(viewType);
        // console.log('============= targetTab ', targetTab);
        if (targetTab) {
            tPanel.setActiveTab(targetTab);
        } else {
            if (record.data.mClassPath) {
                targetTab = Ext.create(record.data.mClassPath);
                // console.log('============= create targetTab ', targetTab);
                tPanel.add(targetTab);
                tPanel.setActiveTab(targetTab);
            }
        }

    }

});
