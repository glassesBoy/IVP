/**
 * 打分
 */
Ext.define('ivp.view.rating.RatingView', {
    extend : 'ivp.desktop.core.Module',
    alias : 'widget.ratingview',
    itemId : 'ratingviewId',

    init : function() {
        this.launcher = {
            text : '评分标准',
            iconCls : 'icon-grid'
        };
    },
    createWindow : function() {
        var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('ratingviewId');
        if (!win) {

            var ratingStore = Ext.create('Ext.data.Store', {
                fields : ['id', 'ratingName', 'ratingValue', 'notes'],
                autoLoad : true,
                proxy : {
                    type : 'ajax',
                    url : 's/r/s',
                    reader : {
                        type : "json",
                        root : "data",
                        successProperty : 'success',
                        totalProperty : 'total'
                    }
                }
            });

            win = desktop.createWindow({
                id : 'ratingviewId',
                title : '评分标准',
                iconCls : 'icon-grid',
                animCollapse : true,
                constrainHeader : true,
                layout : 'border',

                height : 500,
                width : 900,
                items : [{
                    xtype : 'gridpanel',
                    region : 'center',
                    stripeRows : true,
                    columnLines : true,
                    store : ratingStore,
                    split : true,
                    columns : [{
                        xtype : 'gridcolumn',
                        dataIndex : 'ratingName',
                        width : 200,
                        text : '评分规则'

                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'ratingValue',
                        width : 200,
                        text : '分值'

                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'notes',
                        width : 200,
                        text : '说明'

                    }],
                    dockedItems : [{
                        xtype : 'pagingtoolbar',
                        store : ratingStore,
                        dock : 'bottom',
                        displayInfo : true
                    }, {
                        xtype : 'toolbar',
                        dock : 'top',
                        items : [{
                            text : '添加评分',
                            handler : me.addRating
                        }, '-', {
                            text : '删除',
                            handler : me.deleteRating
                        }]
                    }],
                    listeners : {
                        itemdblclick : {
                            fn : me.onUpdateRating,
                            scope : me
                        }
                    }

                }]
            });
        }
        return win;
    },

    addRating : function(btn) {

        var grid = btn.up('grid');
        var gridStore = grid.getStore();
        var createWin = Ext.create('ivp.view.rating.RatingForm', {
            gridStore : gridStore,
            title : '新建评分',
            udt : false
        });
        createWin.show();

    },
    deleteRating : function(btn) {

        var grid = btn.up('grid');
        var record = grid.getSelectionModel().getSelection()[0];
        if (record) {
        } else {
            Ext.MessageBox.alert('', '您好像没有选择任何信息');
            return false
        }

        // console.log('==================record ', record);
        Ext.Msg.confirm("信息", "确定要删除吗？", function(button) {
            if (button == "no") {
                return false;
            }
            var gridStore = grid.getStore();

            var vtID = record.data.id;

            Ext.Ajax.request({
                clientValidation : true,
                url : 's/r/dlt',
                params : {
                    "id" : vtID
                },
                success : function(response, options) {
                    var resp = Ext.decode(response.responseText);
                    Ext.Msg.alert('成功', resp.message);
                    gridStore.load();

                },
                failure : function(response, options) {
                    var resp = Ext.decode(response.responseText);
                    Ext.Msg.alert('失败', resp.message);
                }

            });
        });
    },

    onUpdateRating : function(grid, record, index, eOpts) {

        var gridStore = grid.getStore();
        var updateWin = Ext.create('ivp.view.rating.RatingForm', {
            gridStore : gridStore,
            title : '修改评分',
            udt : true
        });
        updateWin.down('form').getForm().setValues(record.data);
        updateWin.show();

    }

});
