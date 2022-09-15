using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace RoverSensors.Views
{
    public sealed partial class SettingsPage : Page
    {
        public string prefix;
        public string mainurl;
        public string fullurl;

        public SettingsPage()
        {
            this.InitializeComponent();

            //initialize url
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            object _fullurl = localSettings.Values["fullurl"];
            object _prefix = localSettings.Values["prefix"];
            object _mainurl = localSettings.Values["mainurl"];
            if (_fullurl != null)
            {
                fullurl = _fullurl as string;
                prefix = _prefix as string;
                mainurl = _mainurl as string;

                if (fullurl.Count() > 0)
                {
                    //url is set
                    UrlBox.Text = mainurl;
                    if(prefix == "https://")
                    {
                        PrefixBox.SelectedIndex = 0;
                    }
                    else
                    {
                        PrefixBox.SelectedIndex = 1;
                    }
                }
                else
                {
                    fullurl = "https://pliamprojects.000webhostapp.com/rover";
                    prefix = "https://";
                    mainurl = "rovergr.space";

                    UrlBox.Text = mainurl;
                    PrefixBox.SelectedIndex = 0;
                }
            }
            else
            {
                fullurl = "https://pliamprojects.000webhostapp.com/rover";
                prefix = "https://";
                mainurl = "rovergr.space";

                UrlBox.Text = mainurl;
                PrefixBox.SelectedIndex = 0;
            }
        }

        private void DoneButton_Click(object sender, RoutedEventArgs e)
        {
            fullurl = prefix + mainurl;
            //initialize url
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            localSettings.Values["fullurl"] = fullurl;
            localSettings.Values["prefix"] = prefix;
            localSettings.Values["mainurl"] = mainurl;
        }

        private void PrefixBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            prefix = PrefixBox.SelectedItem.ToString();

            if(PrefixBox.SelectedIndex == 0)
            {
                PrefixBox.Background = new SolidColorBrush(Colors.LightGreen);
            }
            else
            {
                PrefixBox.Background = new SolidColorBrush(Colors.LightGray);
            }
        }

        private void UrlBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            if(UrlBox.Text.Count() == 0)
            {
                //empty url
                DoneButton.IsEnabled = false;
                mainurl = "";
            }
            else
            {
                DoneButton.IsEnabled = true;
                mainurl = UrlBox.Text;
            }
        }

        private void StackPanel_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            UrlBox.MaxWidth = TopPanel.ActualWidth - 300;
        }
    }
}
