using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.NetworkInformation;
using System.Threading.Tasks;
using Windows.Data.Json;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using WinRTXamlToolkit.Controls.DataVisualization.Charting;
// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace RoverSensors.Views
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class sensor_data : Page
    {


        public double Hum_avg;
        public double Temp_avg;
        public double Light_avg;
        public double Gas_avg;
        public double CO2_avg;
        public double TVOC_avg;
        public double UV_avg;
        public double BmP_avg;
        public double Alt_avg;
        public double Rad_avg;

        public List<Sensors> Hum_list;
        public List<Sensors> Temp_list;
        public List<Sensors> Light_list;
        public List<Sensors> Gas_list;
        public List<Sensors> CO2_list;
        public List<Sensors> TVOC_list;
        public List<Sensors> UV_list;
        public List<Sensors> BmP_list;
        public List<Sensors> Alt_list;
        public List<Sensors> Rad_list;

        public class Sensors
        {
            public string Date { get; set; }
            public int Value { get; set; }
        }

        public sensor_data()
        {
            this.InitializeComponent();
        }

        public async Task<string> GetUrlAsync()
        {
            string url;
            //initialize url
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            object _fullurl = localSettings.Values["fullurl"];
            if (_fullurl != null)
            {
                string fullurl = _fullurl as string;
                if (fullurl.Count() > 0)
                {
                    url = fullurl;
                }
                else
                {
                    url = "https://pliamprojects.000webhostapp.com/rover";
                }
            }
            else
            {
                url = "https://pliamprojects.000webhostapp.com/rover";
            }

            //check url
            var client = new HttpClient();
            try
            {
                HttpResponseMessage response = await client.GetAsync(new Uri(url + "/habitable_mission.php?mission_id=1"));
                if (response.IsSuccessStatusCode)
                {
                    //success
                    var responseString = await response.Content.ReadAsStringAsync();
                }
                else
                {
                    //fail
                    url = "";
                }
            }
            catch (HttpRequestException e)
            {
                //fail
                url = "";
            }
            finally
            {
                client.Dispose();
            }

            return url;
        }

        public string GetUrl()
        {
            Task<string> get_url = Task.Run(async () => await GetUrlAsync());
            get_url.Wait();
            string url = get_url.Result;
            return url;
        }
        public async Task load_dataAsync()
        {
            bool isInternetConnected = NetworkInterface.GetIsNetworkAvailable();
            if (!isInternetConnected)
            {
                //no internet
                var infoDialog = new ContentDialog
                {
                    Title = "No Internet",
                    Content = "Please check your internet connection and try again",
                    VerticalContentAlignment = VerticalAlignment.Center,
                    HorizontalContentAlignment = HorizontalAlignment.Center,
                    FontSize = 20,
                    PrimaryButtonText = "OK"
                };
                await infoDialog.ShowAsync();
                DataPopup.IsOpen = false;
                DataPopup.Visibility = Visibility.Collapsed;
                return;
            }
            if (GetUrl() == "")
            {
                //fail url
                var infoDialog = new ContentDialog
                {
                    Title = "Invalid Url",
                    Content = "Please insert a valid host url from settings and try again",
                    VerticalContentAlignment = VerticalAlignment.Center,
                    HorizontalContentAlignment = HorizontalAlignment.Center,
                    FontSize = 20,
                    PrimaryButtonText = "OK"
                };
                await infoDialog.ShowAsync();
                DataPopup.IsOpen = false;
                DataPopup.Visibility = Visibility.Collapsed;
                MainPage.GetCurrent().ShowUrlTip();
                return;
            }
            if (GlobalVars.mission_id == 0)
            {
                //no mission selected
                var infoDialog = new ContentDialog
                {
                    Title = "No mission selected",
                    Content = "Please check select a mission and try again",
                    VerticalContentAlignment = VerticalAlignment.Center,
                    HorizontalContentAlignment = HorizontalAlignment.Center,
                    FontSize = 20,
                    PrimaryButtonText = "OK"
                };
                await infoDialog.ShowAsync();
                DataPopup.IsOpen = false;
                DataPopup.Visibility = Visibility.Collapsed;
                MainPage.GetCurrent().ShowSelectTip();
                return;
            }

            (HumChart.Series[0] as LineSeries).ItemsSource = null;
            (TempChart.Series[0] as LineSeries).ItemsSource = null;
            (LightChart.Series[0] as LineSeries).ItemsSource = null;
            (GasChart.Series[0] as LineSeries).ItemsSource = null;
            (CO2Chart.Series[0] as LineSeries).ItemsSource = null;
            (TVOCChart.Series[0] as LineSeries).ItemsSource = null;
            (UVChart.Series[0] as LineSeries).ItemsSource = null;
            (BmPChart.Series[0] as LineSeries).ItemsSource = null;
            (AltChart.Series[0] as LineSeries).ItemsSource = null;
            (RadChart.Series[0] as LineSeries).ItemsSource = null;

            Hum_avg_text.Text = "";
            Temp_avg_text.Text = "";
            Light_avg_text.Text = "";
            Gas_avg_text.Text = "";
            CO2_avg_text.Text = "";
            TVOC_avg_text.Text = "";
            UV_avg_text.Text = "";
            BmP_avg_text.Text = "";
            Alt_avg_text.Text = "";
            Rad_avg_text.Text = "";

            RadGrid.Children.Remove(RadChart);
            RadChart.Loaded += null;
            HumGrid.Children.Remove(HumChart);
            HumChart.Loaded += null;
            TempGrid.Children.Remove(TempChart);
            TempChart.Loaded += null;
            LightGrid.Children.Remove(LightChart);
            LightChart.Loaded += null;
            GasGrid.Children.Remove(GasChart);
            GasChart.Loaded += null;
            CO2Grid.Children.Remove(CO2Chart);
            CO2Chart.Loaded += null;
            TVOCGrid.Children.Remove(TVOCChart);
            TVOCChart.Loaded += null;
            BmPGrid.Children.Remove(BmPChart);
            BmPChart.Loaded += null;
            UVGrid.Children.Remove(UVChart);
            UVChart.Loaded += null;
            AltGrid.Children.Remove(AltChart);
            AltChart.Loaded += null;

            Hum_list = new List<Sensors>();
            Temp_list = new List<Sensors>();
            Light_list = new List<Sensors>();
            Gas_list = new List<Sensors>();
            CO2_list = new List<Sensors>();
            TVOC_list = new List<Sensors>();
            UV_list = new List<Sensors>();
            BmP_list = new List<Sensors>();
            Alt_list = new List<Sensors>();
            Rad_list = new List<Sensors>();

            //get data from source
            var client = new HttpClient();
            HttpResponseMessage response = await client.GetAsync(new Uri(GetUrl()+"/net_data_json.php?mission_id=" + GlobalVars.mission_id.ToString()));
            var jsonString = await response.Content.ReadAsStringAsync();
            JsonArray root = JsonValue.Parse(jsonString).GetArray();
            if (root.Count > 0)
            {
                for (uint i = 0; i < root.Count; i++)
                {
                    string date_time = root.GetObjectAt(i).GetNamedString("date_time");
                    string humidity_str = root.GetObjectAt(i).GetNamedValue("humidity").ToString();
                    string temp_str = root.GetObjectAt(i).GetNamedValue("temperature").ToString();
                    string light_str = root.GetObjectAt(i).GetNamedValue("light").ToString();
                    string gas_str = root.GetObjectAt(i).GetNamedValue("gas").ToString();
                    string CO2_str = root.GetObjectAt(i).GetNamedValue("CO2").ToString();
                    string TVOC_str = root.GetObjectAt(i).GetNamedValue("TVOC").ToString();
                    string UV_str = root.GetObjectAt(i).GetNamedValue("UV").ToString();
                    string BmP_str = root.GetObjectAt(i).GetNamedValue("barometric_pressure").ToString();
                    string alt_str = root.GetObjectAt(i).GetNamedValue("altitude").ToString();
                    string rad_str = root.GetObjectAt(i).GetNamedValue("radiation").ToString();

                    DateTime parsedDate = DateTime.ParseExact(date_time, "dd/MM/yyyy-HH:mm:ss", null);
                    date_time = parsedDate.ToString("HH:mm");

                    Hum_list.Add(new Sensors { Date = date_time, Value = int.Parse(humidity_str) });
                    Temp_list.Add(new Sensors { Date = date_time, Value = int.Parse(temp_str) });
                    Light_list.Add(new Sensors { Date = date_time, Value = int.Parse(light_str) });
                    Gas_list.Add(new Sensors { Date = date_time, Value = int.Parse(gas_str) });
                    CO2_list.Add(new Sensors { Date = date_time, Value = int.Parse(CO2_str) });
                    TVOC_list.Add(new Sensors { Date = date_time, Value = int.Parse(TVOC_str) });
                    UV_list.Add(new Sensors { Date = date_time, Value = int.Parse(UV_str) });
                    BmP_list.Add(new Sensors { Date = date_time, Value = int.Parse(BmP_str) });
                    Alt_list.Add(new Sensors { Date = date_time, Value = int.Parse(alt_str) });
                    Rad_list.Add(new Sensors { Date = date_time, Value = int.Parse(rad_str) });

                }
                //initialize graph and avg values
                if (Hum_list.Count > 0)
                {
                    Hum_avg = Hum_list.Average(item => item.Value);
                    Hum_avg_text.Text = Math.Round(Hum_avg).ToString();
                    Temp_avg = Temp_list.Average(item => item.Value);
                    Temp_avg_text.Text = Math.Round(Temp_avg).ToString();
                    Light_avg = Light_list.Average(item => item.Value);
                    Light_avg_text.Text = Math.Round(Light_avg).ToString();
                    Gas_avg = Gas_list.Average(item => item.Value);
                    Gas_avg_text.Text = Math.Round(Gas_avg).ToString();
                    CO2_avg = CO2_list.Average(item => item.Value);
                    CO2_avg_text.Text = Math.Round(CO2_avg).ToString();
                    TVOC_avg = TVOC_list.Average(item => item.Value);
                    TVOC_avg_text.Text = Math.Round(TVOC_avg).ToString();
                    UV_avg = UV_list.Average(item => item.Value);
                    UV_avg_text.Text = Math.Round(UV_avg).ToString();
                    BmP_avg = BmP_list.Average(item => item.Value);
                    BmP_avg_text.Text = Math.Round(BmP_avg).ToString();
                    Alt_avg = Alt_list.Average(item => item.Value);
                    Alt_avg_text.Text = Math.Round(Alt_avg).ToString();
                    Rad_avg = Rad_list.Average(item => item.Value);
                    Rad_avg_text.Text = Math.Round(Rad_avg).ToString();
                }

                RadGrid.Children.Add(RadChart);
                HumGrid.Children.Add(HumChart);
                TempGrid.Children.Add(TempChart);
                LightGrid.Children.Add(LightChart);
                GasGrid.Children.Add(GasChart);
                CO2Grid.Children.Add(CO2Chart);
                TVOCGrid.Children.Add(TVOCChart);
                UVGrid.Children.Add(UVChart);
                BmPGrid.Children.Add(BmPChart);
                AltGrid.Children.Add(AltChart);

                (HumChart.Series[0] as LineSeries).ItemsSource = Hum_list;
                (TempChart.Series[0] as LineSeries).ItemsSource = Temp_list;
                (LightChart.Series[0] as LineSeries).ItemsSource = Light_list;
                (GasChart.Series[0] as LineSeries).ItemsSource = Gas_list;
                (CO2Chart.Series[0] as LineSeries).ItemsSource = CO2_list;
                (TVOCChart.Series[0] as LineSeries).ItemsSource = TVOC_list;
                (UVChart.Series[0] as LineSeries).ItemsSource = UV_list;
                (BmPChart.Series[0] as LineSeries).ItemsSource = BmP_list;
                (AltChart.Series[0] as LineSeries).ItemsSource = Alt_list;
                (RadChart.Series[0] as LineSeries).ItemsSource = Rad_list;

                RadChart.Loaded += new RoutedEventHandler((sender, e) =>
                {
                    DataPopup.IsOpen = false;
                    DataPopup.Visibility = Visibility.Collapsed;
                });
            }
            else
            {
                DataPopup.IsOpen = false;
                DataPopup.Visibility = Visibility.Collapsed;
            }
        }

        private async void Page_Loaded(object sender, RoutedEventArgs e)
        {
            PositionPopup();
            DataPopup.IsOpen = true;
            DataPopup.Visibility = Visibility.Visible;
            await Window.Current.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal, async () =>
            {
                configChartStyle(HumChart);
                configChartStyle(TempChart);
                configChartStyle(LightChart);
                configChartStyle(GasChart);
                configChartStyle(CO2Chart);
                configChartStyle(TVOCChart);
                configChartStyle(UVChart);
                configChartStyle(BmPChart);
                configChartStyle(AltChart);
                configChartStyle(RadChart);

                await load_dataAsync();

            });
        }

        public void configChartStyle(Chart sender)
        {
            Style legendStyle = new Style();
            legendStyle.TargetType = typeof(Control);
            Setter setterWidth = new Setter(WidthProperty, 0);
            Setter setterHeight = new Setter(HeightProperty, 0);
            legendStyle.Setters.Add(setterWidth);
            legendStyle.Setters.Add(setterHeight);
            sender.LegendStyle = legendStyle;

            Style titleStyle = new Style();
            titleStyle.TargetType = typeof(Control);
            titleStyle.Setters.Add(setterWidth);
            titleStyle.Setters.Add(setterHeight);
            sender.TitleStyle = titleStyle;

            Style pointStyle = new Style();
            pointStyle.TargetType = typeof(LineDataPoint);
            pointStyle.Setters.Add(setterHeight);
            pointStyle.Setters.Add(setterWidth);
            (sender.Series[0] as LineSeries).DataPointStyle = pointStyle;


        }

        private void GridView_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            PositionPopup();
        }

        public void PositionPopup()
        {
            //Stretch popup
            DataPopupGrid.Width = DataGrid.ActualWidth;
            DataPopupGrid.Height = Window.Current.Bounds.Height - 20;

            DataPopupBorder.Width = DataGrid.ActualWidth;
            DataPopupBorder.Height = Window.Current.Bounds.Height - 20;

            DataPopup.Width = DataGrid.ActualWidth;
            DataPopup.Height = Window.Current.Bounds.Height - 20;
        }

        private async void DataRefresh_ClickAsync(object sender, RoutedEventArgs e)
        {
            PositionPopup();
            DataPopup.IsOpen = true;
            DataPopup.Visibility = Visibility.Visible;
            await Window.Current.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal, async () =>
            {
                configChartStyle(HumChart);
                configChartStyle(TempChart);
                configChartStyle(LightChart);
                configChartStyle(GasChart);
                configChartStyle(CO2Chart);
                configChartStyle(TVOCChart);
                configChartStyle(UVChart);
                configChartStyle(BmPChart);
                configChartStyle(AltChart);
                configChartStyle(RadChart);

                await load_dataAsync();

            });
        }
    }
}
