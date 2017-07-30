/**
 * 车辆管理
 */
Ext.define('ivp.view.VehicleView', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.vehicleview',
    itemId : 'vehicleviewId',
    requires : ['ivp.model.VehicleModel'],

    layout : 'border',
    title : '车辆信息',
    initComponent : function() {
        var me = this;
        var teamStore = Ext.create('Ext.data.Store', {
            fields : ['id', 'teamName', 'logoPath'],
            autoLoad : true,
            proxy : {
                type : 'ajax',
                url : 's/vt/s',
                reader : {
                    type : "json",
                    root : "data",
                    successProperty : 'success',
                    totalProperty : 'total'
                }
            }
        });

        var vStore = Ext.create('Ext.data.Store', {
            model : 'ivp.model.VehicleModel',
          //  autoLoad : true,
            proxy : {
                type : 'ajax',
                url : 's/v/s',
                reader : {
                    type : "json",
                    root : "data",
                    successProperty : 'success',
                    totalProperty : 'total'
                }
            }
        });

        Ext.applyIf(me, {
            items : [{
                xtype : 'gridpanel',
                title : '车队信息',
                region : 'west',
                width : 200,
                stripeRows : true,
                columnLines : true,
                store : teamStore,
                split : true,
                columns : [{
                    xtype : 'gridcolumn',
                    dataIndex : 'teamName',
                    width : 200,
                    text : '车队名称'
                }],
                dockedItems : [{
                    xtype : 'pagingtoolbar',
                    store : teamStore,
                    dock : 'bottom',
                    displayInfo : true
                }, {
                    xtype : 'toolbar',
                    dock : 'top',
                    items : [{
                        text : '添加车队',
                        handler : me.addVehicleTeam
                    }, '-', {
                        text : '删除',
                        handler : me.deleteVehicleTeam
                    }]
                }],
                listeners : {
                    select : {
                        fn : me.onTeamSelect,
                        scope : me
                    }
                }

            }, {
                xtype : 'gridpanel',
                title : '车辆信息',
                region : 'center',
                stripeRows : true,
                columnLines : true,
                store : vStore,
                columns : [{
                    xtype : 'rownumberer',
                    width : 50
                }, {
                    xtype : 'gridcolumn',
                    dataIndex : 'vName',
                    // format : 'Y-m-d H:i:s',
                    text : '车辆名称'
                }, {
                    xtype : 'gridcolumn',
                    dataIndex : 'vCode',
                    text : '车牌号码'
                }, {
                    xtype : 'gridcolumn',
                    dataIndex : 'teamName',
                    text : '所属车队'
                }, {
                    xtype : 'gridcolumn',
                    dataIndex : 'vLength',
                    text : '长度'
                }, {
                    xtype : 'gridcolumn',
                    dataIndex : 'vWidth',
                    text : '宽度'
                }],
                dockedItems : [{
                    xtype : 'pagingtoolbar',
                    store : vStore,
                    dock : 'bottom',
                    displayInfo : true
                }, {
                    xtype : 'toolbar',
                    dock : 'top',
                    items : [{
                        text : '添加车辆',
                        handler : me.addVehicle
                    }, '-', {
                        text : '删除',
                        handler : me.deleteVehicle
                    }]
                }],
                listeners : {
                    itemdblclick : {
                        fn : me.onUserDBclick,
                        scope : me
                    }
                }

            }]
        });
        me.callParent(arguments);
    },

    onTeamSelect : function(rowmodel, record, index, eOpts) {
        // console.log('============== rowmodel ', rowmodel);
        // console.log('============== record ', record);
        if (!record.data.mXType) {
            return false;
        }

        var treepanel = rowmodel.view.up('gridpanel');
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
